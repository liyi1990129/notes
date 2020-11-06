<template>
  <el-dialog
    title="条件关联"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-row>
      <el-row style="margin-bottom: 10px;">
        <el-col :span="4">
          <el-button type="primary" @click="addItem()">新增</el-button>
        </el-col>
      </el-row>

      <el-table
        :data="conditionList"
        border
        style="width: 100%">
        <el-table-column
          prop="conditionName"
          label="条件名称"
          width="180">
            <template slot-scope="scope">
              <el-input v-model="scope.row.conditionName"  placeholder="条件名称"></el-input>
            </template>
        </el-table-column>

        <el-table-column
          prop="conditionExpression"
          label="条件表达式"
          >
          <template slot-scope="scope">
              <el-input v-model="scope.row.conditionExpression" @focus="showV(scope.row,scope.$index)" placeholder="参数值"></el-input>
          </template>
        </el-table-column>
        <el-table-column
          prop="conditionDesc"
          label="条件描述"
          width="180">
          <template slot-scope="scope">
            <el-input v-model="scope.row.conditionDesc"  placeholder="条件描述"></el-input>
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
        <el-button type="primary" @click="getExp">确 定</el-button>
      </span>
    </el-dialog>

  </el-dialog>


</template>

<script>
  import { addCondition, saveCondition } from '@/api/rule'
  import { entityItemList } from '@/api/entity'

  export default {
    data () {
      return {
        entity: null,
        entityItem: null,
        ruleId: null,
        inputV: '',
        listData: [],
        conditionList: [],
        entityInfos: [],
        entityItemInfos: [],
        visible: false,
        dialogVisible: false,
        chooseIndex: null
      }
    },
    created () {

    },
    methods: {
      getExp () {
        this.conditionList[this.chooseIndex].conditionExpression = this.inputV
        this.dialogVisible = false
      },
      showV (data, index) {
        debugger
        this.inputV = data.conditionExpression
        this.chooseIndex = index
        this.dialogVisible = true
      },
      insertV (data) {
        this.inputV = this.inputV + data
      },
      insertV1 (data) {
        this.inputV = this.inputV + '$' + data + '$'
      },
      init (id) {
        this.ruleId = id
        this.visible = true

        if (this.ruleId) {
          let params = {
            id: this.ruleId + ''
          }
          addCondition(params).then(res => {
            if (res.data.data && res.data.resultCode === 0) {
              this.conditionList = res.data.data.relList
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
          ruleId: this.ruleId,
          conditionName: '',
          conditionExpression: '',
          conditionDesc: '',
          isEffect: '1'
        }
        this.conditionList.push(item)
      },
      deleteItem (index, rows) {
        rows.splice(index, 1)
      },

      changeAction (item, row) {
        // let list = this.itemDataForm.itemData.filter(i => i.rulePropertyId === item.rulePropertyId)
        // if (list && list.length > 1) {
        //   row.rulePropertyId = ''
        //   row.rulePropertyName = ''
        //   this.$message.error('属性不能重复')
        // }
        // row.rulePropertyName = item.rulePropertyName
      },

      // 表单提交
      dataFormSubmit () {
        let params = {
          id: this.ruleId + '',
          relInfo: JSON.stringify(this.conditionList)
        }
        saveCondition(params).then(res => {
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
