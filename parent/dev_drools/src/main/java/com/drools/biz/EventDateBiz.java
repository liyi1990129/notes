package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.mapper.EventDateMapper;
import com.drools.model.EventDate;
import com.drools.model.RuleInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EventDateBiz {

    @Resource
    private EventDateMapper eventDateMapper;


    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/20 14:03:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        String crtable = (String) params.get("crtable");

        EventDate info = new EventDate();

        if (!StringUtils.isEmpty(crtable)) {
            info.setCrtable(crtable);
        }

        Page page = PageHelper.startPage(pageNumber, pageSize);
        List<EventDate> list = this.eventDateMapper.list(info);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> item.setList(eventDateMapper.findByCrtable(item.getCrtable())));
        }
        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }


    public List<EventDate> getInfoByCrtable(String crtable) {
        return eventDateMapper.findByCrtable(crtable);
    }


    public void delInfoByCrtable(String crtable) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(crtable)) {
            EventDate eventDate = new EventDate();
            eventDate.setCrtable(crtable);
            this.eventDateMapper.delete(eventDate);
        }

    }

    /* *
     * 保存
     * @author ly
     * @modifyTime 2020/11/20 14:04:00
     */
    public void saveOrUpdate(String crtable, List<EventDate> eventDates) {
        delInfoByCrtable(crtable);
        if (!CollectionUtils.isEmpty(eventDates)) {
            eventDates.forEach(eventDate -> {
                eventDate.setId(null);
                eventDate.setCrtable(crtable);
                this.eventDateMapper.insert(eventDate);
            });
        }
    }

}
