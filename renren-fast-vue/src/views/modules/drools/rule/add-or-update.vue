<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

      <el-row>
        <el-col :span="12">
          <el-form-item label="模型标识" prop="entityIdentify">
            <el-select v-model="dataForm.entityIdentify" filterable placeholder="请选择">
              <el-option
                v-for="item in entitys"
                :key="item.name"
                :label="item.name"
                :value="item.name"
                @click.native="changeEntity(item)">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="模型名称" prop="entityName">
            <el-input v-model="dataForm.entityName" placeholder="模型名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="包路径" prop="pkgName">
            <el-input v-model="dataForm.pkgName" placeholder="包路径"></el-input>
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
      <el-form-item  label="备注" prop="remark">
        <el-input  type="textarea"  :rows="2" v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
    </el-form>

    <div>
      <div class="title">属性</div>
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
          prop="itemIdentify"
          label="属性标识"
          width="180">
          <template slot-scope="scope">
            <el-form-item
              :rules="itemRule.itemIdentify"
              :prop="'itemData.'+scope.$index+'.itemIdentify'"
            >
            <el-select v-model="scope.row.itemIdentify" filterable placeholder="请选择">
              <el-option
                v-for="item in entitysProperties"
                :key="item"
                :label="item"
                :value="item"
                @click.native="changeEntityItem(item,scope.row)">
              </el-option>
            </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="itemName"
          label="属性名称"
          width="180">
          <template slot-scope="scope">
            <el-form-item
              :rules="itemRule.itemName"
              :prop="'itemData.'+scope.$index+'.itemName'"
            >
            <el-input v-model="scope.row.itemName" placeholder="属性名称"></el-input>
            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column
          prop="itemDesc"
          label="属性描述">
          <template slot-scope="scope">
            <el-form-item class="tb-colum">
              <el-input v-model="scope.row.itemDesc" placeholder="属性描述"></el-input>
            </el-form-item>
          </template>
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
  import { getEntitys, getEntityProperties, saveOrUpdate, getInfo } from '@/api/entity'

  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          entityName: '',
          entityDesc: '',
          entityIdentify: '',
          pkgName: '',
          remark: '',
          isEffect: 1
        },
        entitys: [],
        entitysProperties: [],
        dataRule: {
          entityName: [
            { required: true, message: '模型名称不能为空', trigger: 'blur' }
          ],
          entityIdentify: [
            { required: true, message: '模型标识不能为空', trigger: 'change' }
          ]
        },
        itemDataForm: {
          itemData: []
        },
        itemRule: {
          itemName: [
            { required: true, message: '属性名称不能为空', trigger: 'blur' }
          ],
          itemIdentify: [
            { required: true, message: '属性标识不能为空', trigger: 'change' }
          ]
        }
      }
    },
    created () {
      this.getEntityCls()
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
              this.dataForm = res.data.data.entity
              this.itemDataForm.itemData = res.data.data.entityItems
              this.entitysProperties = res.data.data.proList
            }
          })
        }
      },

      // 实体类改变
      changeEntity (item) {
        this.dataForm.entityIdentify = item.name
        this.dataForm.pkgName = item.className
        this.entitysProperties = []
        this.itemData = []
        this.getEntityProperties()
      },

      // 获取实体类列表
      getEntityCls () {
        getEntitys({}).then(res => {
          if (res.data.data) {
            this.entitys = res.data.data
          }
        })
      },

      // 获取实体类的属性
      getEntityProperties () {
        let params = {
          className: this.dataForm.pkgName
        }
        getEntityProperties(params).then(res => {
          if (res.data.data) {
            this.entitysProperties = res.data.data
          }
        })
      },

      // 新增属性
      addItem () {
        let item = {
          itemName: '',
          itemIdentify: '',
          itemDesc: ''
        }
        this.itemDataForm.itemData.push(item)
      },

      deleteItem (index, rows) {
        rows.splice(index, 1)
      },

      changeEntityItem (item, row) {
        let list = this.itemDataForm.itemData.filter(i => i.itemIdentify === item)
        if (list && list.length >= 1) {
          row.itemIdentify = ''
          this.$message.error('属性标识不能重复')
        }
      },

      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$refs['dataItemForm'].validate((valid1) => {
              if (valid1) {
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
