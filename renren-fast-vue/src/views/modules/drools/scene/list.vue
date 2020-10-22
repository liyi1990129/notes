<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item label="场景类型">
        <el-input v-model="dataForm.sceneType" placeholder="场景类型" clearable></el-input>
      </el-form-item>
      <el-form-item label="场景标识">
        <el-input v-model="dataForm.sceneIdentify" placeholder="场景标识" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('sys:user:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
    </el-form>

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
        prop="sceneIdentify"
        header-align="center"
        align="center"
        label="场景标识">
      </el-table-column>
      <el-table-column
        prop="sceneName"
        header-align="center"
        align="center"
        width="120"
        label="场景名称">
      </el-table-column>
      <el-table-column
        prop="sceneType"
        header-align="center"
        align="center"
        label="场景类型">
      </el-table-column>
      <el-table-column
        prop="sceneDesc"
        header-align="center"
        align="center"
        label="场景说明">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('sys:menu:update')" type="text" size="small" @click="addOrUpdateHandle(scope.row.sceneId)">修改</el-button>
          <el-button v-if="isAuth('sys:menu:delete')" type="text" size="small" @click="deleteHandle(scope.row.sceneId)">删除</el-button>
        </template>
      </el-table-column>
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

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>

  </div>
</template>

<script>
  import { pageList, del } from '@/api/scene'
  import AddOrUpdate from './add-or-update'

  export default {
    data () {
      return {
        dataForm: {
          sceneType: '',
          sceneIdentify: ''
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
      AddOrUpdate
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
          pageSize: this.page.pageSize,
          entityName: this.dataForm.entityName,
          entityIdentify: this.dataForm.entityIdentify
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
      },
      // 删除
      deleteHandle (id) {
        this.$confirm(`确定对[id=${id}]进行[删除]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            id: id
          }
          del(params).then(res => {
            if (res.data && res.data.resultCode === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(res.data.resultMsg)
            }
          })
        }).catch(() => {})
      }
    }
  }
</script>
<style>
  body .el-table th.gutter {
    display: table-cell !important
  }
</style>
