<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

      <el-row>
        <el-col :span="12">
          <el-form-item label="所属场景" prop="sceneId">
            <el-select v-model="dataForm.sceneId" filterable placeholder="请选择">
              <el-option
                v-for="item in sceneListData"
                :key="item.sceneId"
                :label="item.sceneName"
                :value="item.sceneId"
                @click.native="changeScene(item)">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="规则名称" prop="ruleName">
            <el-input v-model="dataForm.ruleName" placeholder="模型名称"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="是否启用" prop="pkgName">
            <el-switch
              v-model="dataForm.ruleEnabled"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              active-text="是"
              inactive-text="否"
              inactive-value="0">
            </el-switch>
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
      <el-form-item  label="描述" prop="ruleDesc">
        <el-input v-model="dataForm.ruleDesc" placeholder="描述"></el-input>
      </el-form-item>
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
          prop="rulePropertyId"
          label="属性标识"
          width="180">
          <template slot-scope="scope">
            <el-form-item
              :rules="itemRule.rulePropertyId"
              :prop="'itemData.'+scope.$index+'.rulePropertyId'"
            >
            <el-select v-model="scope.row.rulePropertyId" filterable placeholder="请选择">
              <el-option
                v-for="item in ruleProperties"
                :key="item.rulePropertyId"
                :label="item.rulePropertyIdentify"
                :value="item.rulePropertyId"
                @click.native="changeEntityItem(item,scope.row)">
              </el-option>
            </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column
          prop="rulePropertyName"
          label="属性名称"
          width="180">
          <template slot-scope="scope">
            <el-form-item
              :rules="itemRule.rulePropertyName"
              :prop="'itemData.'+scope.$index+'.rulePropertyName'"
            >
            <el-input v-model="scope.row.rulePropertyName" disabled placeholder="属性名称"></el-input>
            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column
          prop="rulePropertyValue"
          label="属性值">
          <template slot-scope="scope">
            <el-form-item class="tb-colum">
              <el-input v-model="scope.row.rulePropertyValue" placeholder="属性描述"></el-input>
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
  import { sceneList, propertyList, saveOrUpdate, getInfo } from '@/api/rule'

  export default {
    data () {
      return {
        sceneListData: [],
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
        ruleProperties: [],
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
          rulePropertyValue: [
            { required: true, message: '属性值不能为空', trigger: 'blur' }
          ],
          rulePropertyName: [
            { required: true, message: '属性名称不能为空', trigger: 'blur' }
          ],
          rulePropertyId: [
            { required: true, message: '规则属性不能为空', trigger: 'change' }
          ]
        }
      }
    },
    created () {
      this.initScene()
    },
    methods: {
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
              this.itemDataForm.itemData = res.data.data.relList
            }
          })
        }
      },
      initScene () {
        let params = {
          isEffect: 1
        }
        sceneList(params).then(res => {
          this.sceneListData = res.data.data
        })

        propertyList(params).then(res => {
          this.ruleProperties = res.data.data
        })
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
        let list = this.itemDataForm.itemData.filter(i => i.rulePropertyId === item.rulePropertyId)
        if (list && list.length > 1) {
          row.rulePropertyId = ''
          row.rulePropertyName = ''
          this.$message.error('属性不能重复')
        }
        row.rulePropertyName = item.rulePropertyName
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
