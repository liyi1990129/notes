<template>
  <div class="mod-menu">
    <!-- 列表 -->
    <el-table
      :data="dataList"
      border
      style="width: 100%; ">
      <el-table-column
        type="index"
        header-align="center"
        width="50"
        label="序号" >
      </el-table-column>
      <el-table-column
        prop="rulePropertyIdentify"
        header-align="center"
        align="center"
        width="120"
        label="标识">
      </el-table-column>
      <el-table-column
        prop="rulePropertyName"
        header-align="center"
        align="center"
        width="120"
        label="名称">
      </el-table-column>
      <el-table-column
        prop="rulePropertyDesc"
        header-align="center"
        align="center"
        label="描述">
      </el-table-column>
      <el-table-column
        prop="defaultValue"
        header-align="center"
        align="center"
        width="150"
        label="默认值">
      </el-table-column>

<!--      <el-table-column-->
<!--        header-align="center"-->
<!--        align="center"-->
<!--        width="150"-->
<!--        label="操作">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button v-if="isAuth('sys:menu:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.entityId)">修改</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="page.pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="page.pageSize"
      :total="page.totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>


  </div>
</template>

<script>
  import { pageList } from '@/api/property'

  export default {
    data () {
      return {
        dataForm: {
          entityName: '',
          entityIdentify: ''
        },
        dataList: [],
        dataListLoading: false,
        addOrUpdateVisible: false,
        page: {
          pageIndex: 1,
          pageSize: 10,
          totalPage: 0
        }
      }
    },
    components: {
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 每页数
      sizeChangeHandle (val) {
        this.page.pageSize = val
        this.page.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.page.pageIndex = val
        this.getDataList()
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        let params = {
          pageNumber: this.page.pageIndex,
          pageSize: this.page.pageSize
        }
        pageList(params).then(res => {
          this.dataListLoading = false
          this.dataList = res.data.data.list
          this.page.totalPage = res.data.data.total
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      }

    }
  }
</script>
<style>
  body .el-table th.gutter {
    display: table-cell !important
  }
</style>
