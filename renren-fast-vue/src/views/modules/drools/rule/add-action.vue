<template>
  <el-dialog
    title="动作关联"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row style="margin-bottom: 10px;">
      <el-col :span="4">
        <el-button type="primary" @click="addItem()">新增</el-button>
      </el-col>
    </el-row>
    <el-row v-for="(action,index) in listData">
      <el-row style="margin-bottom: 10px;">
        <el-col :span="4">
          动作名称：
        </el-col>
        <el-col :span="4">
          <el-select v-model="action.actionId" filterable placeholder="请选择动作">
            <el-option
              v-for="item in actionList"
              :key="item.actionId"
              :label="item.actionName"
              :value="item.actionId"
              @click.native="changeAction(item,index)">
            </el-option>
          </el-select>
        </el-col>
      </el-row>

      <el-table
        :data="action.relList"
        border
        style="width: 100%">
        <el-table-column
          prop="actionParamId"
          label="动作参数"
          width="180">
          <template slot-scope="scope">
              <el-select v-model="scope.row.actionParamId" filterable placeholder="请选择">
                <el-option
                  v-for="item in action.paramInfoList"
                  :key="item.actionParamId"
                  :label="item.actionParamName"
                  :value="item.actionParamId"
                  @click.native="changeEntityItem(item,scope.row)">
                </el-option>
              </el-select>
          </template>
        </el-table-column>
        <el-table-column
          prop="paramValue"
          label="参数值"
          >
          <template slot-scope="scope">
              <el-input v-model="scope.row.paramValue" @focus="showV(scope.row,scope.$index,action,index)" placeholder="参数值"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          label="是否启用"
          width="180">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.isEffect"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              active-text="是"
              inactive-text="否"
              inactive-value="0">
            </el-switch>
          </template>
        </el-table-column>
      </el-table>
    </el-row>


    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="50%"
      append-to-body>
      <el-row>
        <el-col :span="2" class="mr10">
          <span @click="insertV('=')" class="btn-span">=</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('+')" class="btn-span">+</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('-')" class="btn-span">-</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('*')" class="btn-span">*</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('/')" class="btn-span">/</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('(')" class="btn-span">(</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV(')')" class="btn-span">)</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('>')" class="btn-span">></span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('<')" class="btn-span"><</span>
        </el-col>
        <el-col :span="2" class="mr10">
          <span @click="insertV('!')" class="btn-span">!</span>
        </el-col>
      </el-row>

      <el-row style="padding: 10px 0;">
        <el-col :span="3" class="mr10">
          <span @click="insertV('NOW')" class="btn-span">当前时间</span>
        </el-col>
      </el-row>

      <el-row style="padding: 10px 0;">
        <el-col :span="4">
          实体属性
        </el-col>
        <el-col :span="4" class="mr10">
          <el-select v-model="entity" filterable clearable  placeholder="请选择">
            <el-option
              v-for="item in entityInfos"
              :key="item.entityId"
              :label="item.entityName"
              :value="item.entityId"
              @click.native="changeEntityItem(item)">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="entityItem" filterable clearable  placeholder="请选择">
            <el-option
              v-for="item in entityItemInfos"
              :key="item.itemId"
              :label="item.itemName"
              :value="item.itemId"
              @click.native="insertV1(item.itemId)">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="4">
          属性值
        </el-col>
        <el-col :span="18">
          <el-input v-model="inputV"></el-input>
        </el-col>

      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="getValue">确 定</el-button>
      </span>
    </el-dialog>

  </el-dialog>


</template>

<script>
  import { addAction, saveAction } from '@/api/rule'
  import { getInfo } from '@/api/action'
  import { entityItemList } from '@/api/entity'

  export default {
    data () {
      return {
        entity: null,
        entityItem: null,
        ruleId: null,
        inputV: '',
        listData: [],
        actionList: [],
        entityInfos: [],
        entityItemInfos: [],
        visible: false,
        dialogVisible: false,
        chooseIndex: null,
        chooseActionIndex: null
      }
    },
    created () {

    },
    methods: {
      getValue () {
        this.listData[this.chooseActionIndex].relList[this.chooseIndex].paramValue = this.inputV
        this.dialogVisible = false
      },
      showV (data, i, action, index) {
        this.inputV = data.paramValue
        this.chooseActionIndex = index
        this.chooseIndex = i
        this.dialogVisible = true
      },
      insertV (data) {
        this.inputV = this.inputV + data
      },
      insertV1 (data) {
        this.inputV = this.inputV + '#' + data + '#'
      },
      init (id) {
        this.ruleId = id
        this.visible = true

        if (this.ruleId) {
          let params = {
            id: this.ruleId + ''
          }
          addAction(params).then(res => {
            if (res.data.data && res.data.resultCode === 0) {
              this.actionList = res.data.data.actionList
              this.listData = res.data.data.paramValueInfoList
              this.entityInfos = res.data.data.entityInfos
            }
          })
        }
      },

      changeEntityItem (item) {
        let params = {
          id: item.entityId + ''
        }
        entityItemList(params).then(res => {
          this.entityItemInfos = res.data.data
        })
      },
      addItem () {
        let item = {
          actionId: null,
          ruleId: this.ruleId,
          relList: []
        }
        this.listData.push(item)
      },
      deleteItem (index, rows) {
        rows.splice(index, 1)
      },

      changeAction (item, row) {
        debugger
        let actionId = item.actionId
        let params = {
          id: actionId + ''
        }
        getInfo(params).then(res => {
          if (res.data.data && res.data.resultCode === 0) {
            this.listData[row].relList = []
            res.data.data.actionItems.forEach(item => {
              const paramValue = {
                actionParamId: item.actionParamId,
                paramValue: '',
                isEffect: '1',
                ruleId: this.ruleId,
                actionId: actionId
              }
              this.listData[row].relList.push(paramValue)
            })

            this.listData[row].paramInfoList = res.data.data.actionItems
          }
        })
      },

      // 表单提交
      dataFormSubmit () {
        let params = {
          id: this.ruleId + '',
          relInfo: JSON.stringify(this.listData)
        }
        saveAction(params).then(res => {
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
  .btn-span{
    display: block;
    text-align: center;
    border: 1px solid #dcdfe6;
    border-radius: 5px;
    cursor: pointer;
  }
  .mr10{
    margin-right: 10px;
  }
</style>
