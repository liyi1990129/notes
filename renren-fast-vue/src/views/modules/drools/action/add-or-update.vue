<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

      <el-row>
        <el-col :span="12">
          <el-form-item label="动作类型" prop="actionType">
            <el-select v-model="dataForm.actionType" filterable placeholder="请选择">
              <el-option
                v-for="item in actionTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                @click.native="changeScene(item)">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="动作名称" prop="actionName">
            <el-input v-model="dataForm.actionName" placeholder="模型名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item  label="动作描述" prop="">
            <el-input v-model="dataForm.actionDesc" placeholder="动作描述"></el-input>
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
      <el-form-item  label="动作实现类" prop="">
        <el-select v-model="dataForm.actionClass" filterable placeholder="请选择">
          <el-option
            v-for="item in entitys"
            :key="item.pkgName"
            :label="item.entityName"
            :value="item.pkgName"
            @click.native="changeEntity(item,scope.row)">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item  label="备注" prop="remark">
        <el-input  type="textarea"  :rows="2" v-model="dataForm.remark" placeholder="备注"></el-input>
      </el-form-item>
    </el-form>

    <div>
      <div class="title">参数</div>
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
          prop="paramIdentify"
          label="属性标识"
          width="180">
          <template slot-scope="scope">
            <el-form-item
              :rules="itemRule.paramIdentify"
              :prop="'itemData.'+scope.$index+'.paramIdentify'"
            >
            <el-select v-model="scope.row.paramIdentify" filterable placeholder="请选择">
              <el-option
                v-for="item in entitysProperties"
                :key="item.itemIdentify"
                :label="item.itemName"
                :value="item.itemIdentify"
                @click.native="changeEntityItem(item,scope.row)">
              </el-option>
            </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="actionParamName"
          label="属性名称"
          width="180">
          <template slot-scope="scope">
            <el-form-item
              :rules="itemRule.actionParamName"
              :prop="'itemData.'+scope.$index+'.actionParamName'"
            >
            <el-input v-model="scope.row.actionParamName" disabled placeholder="属性名称"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="actionParamDesc"
          label="属性描述"
          width="180">
          <template slot-scope="scope">
            <el-form-item
              :rules="itemRule.actionParamDesc"
              :prop="'itemData.'+scope.$index+'.actionParamDesc'"
            >
            <el-input v-model="scope.row.actionParamDesc" disabled placeholder="属性描述"></el-input>
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
  import { saveOrUpdate, getInfo } from '@/api/action'
  import { entityList, entityItemList } from '@/api/entity'

  export default {
    data () {
      return {
        actionTypes: [
          {label: '实现', value: 1},
          {label: '自身', value: 2}
        ],
        visible: false,
        dataForm: {
          id: 0,
          actionType: '',
          actionName: '',
          actionClass: '',
          actionDesc: '',
          remark: '',
          isEffect: 1
        },
        entitys: [],
        entitysProperties: [],
        dataRule: {
          ruleName: [
            { required: true, message: '规则名称不能为空', trigger: 'blur' }
          ],
          sceneId: [
            { required: true, message: '所属场景不能为空', trigger: 'change' }
          ]
        },
        itemDataForm: {
          itemData: []
        },
        itemRule: {
          paramIdentify: [
            { required: true, message: '属性标识不能为空', trigger: 'blur' }
          ],
          actionParamName: [
            { required: true, message: '属性名称不能为空', trigger: 'blur' }
          ],
          actionParamDesc: [

          ]
        }
      }
    },
    created () {
      this.getEntityCls()
    },
    methods: {
      // 获取实体类列表
      getEntityCls () {
        entityList({}).then(res => {
          if (res.data.data) {
            this.entitys = res.data.data
          }
        })
      },
      changeEntity (item, row) {
        this.getEntityProperties(item.entityId)
      },
      // 获取实体类的属性
      getEntityProperties (id) {
        let params = {
          id: id
        }
        entityItemList(params).then(res => {
          if (res.data.data) {
            this.entitysProperties = res.data.data
          }
        })
      },
      init (id) {
        this.dataForm.id = id
        this.visible = true

        if (this.dataForm.id) {
          let params = {
            id: this.dataForm.id + ''
          }
          getInfo(params).then(res => {
            if (res.data.data && res.data.resultCode === 0) {
              this.dataForm = res.data.data.info
              this.itemDataForm.itemData = res.data.data.actionItems
            }
          })
        }
      },
      // 场景改变
      changeScene (item) {
      },

      // 新增属性
      addItem () {
        let item = {
          rulePropertyId: '',
          rulePropertyValue: '',
          rulePropertyName: ''
        }
        this.itemDataForm.itemData.push(item)
      },

      deleteItem (index, rows) {
        rows.splice(index, 1)
      },

      changeEntityItem (item, row) {
        debugger
        let list = this.itemDataForm.itemData.filter(i => i.paramIdentify === item.paramIdentify)
        if (list && list.length > 1) {
          row.paramIdentify = ''
          row.actionParamName = ''
          row.actionParamDesc = ''
          this.$message.error('属性不能重复')
        }
        row.actionParamName = item.itemName
        row.actionParamDesc = item.itemDesc
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
