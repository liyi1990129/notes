<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

      <el-row>
        <el-col :span="12">
          <el-form-item label="场景标识" prop="sceneIdentify">
            <el-input v-model="dataForm.sceneIdentify" placeholder="场景标识"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="场景名称" prop="sceneName">
            <el-input v-model="dataForm.sceneName" placeholder="场景名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="场景类型" prop="sceneType">
            <el-select v-model="dataForm.sceneType" filterable placeholder="请选择">
              <el-option
                v-for="item in sceneTypes"
                :key="item.name"
                :label="item.name"
                :value="item.name">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否有效" prop="isEffect">
            <el-switch
              v-model="dataForm.isEffect"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              active-text="是"
              inactive-text="否"
              inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item  label="描述" prop="sceneDesc">
        <el-input   v-model="dataForm.sceneDesc" placeholder="描述"></el-input>
      </el-form-item>
      <el-form-item  label="备注" prop="remark">
        <el-input  type="textarea"  :rows="2" v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
    </el-form>

    <div>
      <div class="title">实体</div>
      <el-row style="margin-bottom: 10px;">
        <el-col :span="4">
          <el-button type="primary" @click="addItem()">新增</el-button>
        </el-col>
      </el-row>

      <el-form  ref="dataItemForm" :model="itemDataForm"  label-width="80px">

      <el-table
        :data="itemDataForm.itemData"
        height="250"
        border
        style="width: 100%">
        <el-table-column
          prop="entityIdentify"
          label="实体标识"
          width="180">
          <template slot-scope="scope">
            <el-form-item>
            <el-select v-model="scope.row.entityIdentify" filterable placeholder="请选择">
              <el-option
                v-for="item in entitysProperties"
                :key="item.entityId"
                :label="item.entityIdentify"
                :value="item.entityId"
                @click.native="changeEntityItem(item,scope.row)">
              </el-option>
            </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="entityName"
          label="实体名称"
          width="180">
        </el-table-column>


        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small"  @click.native.prevent="deleteItem(scope.$index, itemDataForm.itemData)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-form>
    </div>

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import { entityList, saveOrUpdate, getInfo } from '@/api/scene'

  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          sceneName: '',
          sceneDesc: '',
          sceneIdentify: '',
          sceneType: '',
          remark: '',
          isEffect: 1
        },
        sceneTypes: [],
        entitysProperties: [],
        dataRule: {
          sceneName: [
            { required: true, message: '场景名称不能为空', trigger: 'blur' }
          ],
          sceneIdentify: [
            { required: true, message: '场景标识不能为空', trigger: 'change' }
          ]
        },
        itemDataForm: {
          itemData: []
        }
      }
    },
    created () {
      this.getEntityProperties()
    },
    methods: {
      init (id) {
        this.dataForm.id = id
        this.visible = true
        if (this.dataForm.id) {
          let params = {
            id: this.dataForm.id
          }
          getInfo(params).then(res => {
            if (res.data.data && res.data.resultCode === 0) {
              this.dataForm = res.data.data.info
              this.itemDataForm.itemData = res.data.data.list
            }
          })
        }
      },

      // 实体类改变
      changeEntity (item) {
        this.dataForm.entityIdentify = item.name
        this.dataForm.pkgName = item.className
      },

      // 获取实体类的属性
      getEntityProperties () {
        let params = {
          className: this.dataForm.pkgName
        }
        entityList(params).then(res => {
          if (res.data.data) {
            this.entitysProperties = res.data.data
          }
        })
      },

      // 新增属性
      addItem () {
        let item = {
          entityName: '',
          entityIdentify: ''
        }
        this.itemDataForm.itemData.push(item)
      },

      deleteItem (index, rows) {
        rows.splice(index, 1)
      },

      changeEntityItem (item, row) {
        debugger
        let list = this.itemDataForm.itemData.filter(i => i.entityId === item.entityId)
        if (list && list.length >= 1) {
          row.entityIdentify = ''
          row.entityName = ''
          row.entityId = ''
          this.$message.error('标识不能重复')
          return
        }
        row.entityName = item.entityName
        row.entityId = item.entityId
      },

      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            let params = {
              entity: JSON.stringify(this.dataForm),
              entityItems: JSON.stringify(this.itemDataForm.itemData)
            }
            saveOrUpdate(params).then(res => {
              if (res.data && res.data.resultCode === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(res.data.resultMsg)
              }
            })
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  .title{
    padding: 10px;
    background-color: rgba(248, 248, 248, 1);
    margin-bottom: 10px;
  }
  .el-table__row .el-form-item__content{
    margin-left: 0 !important;
  }
  .el-table__row .el-form-item{
    margin-bottom: 0 !important;
  }
</style>
