<template>
  <el-dialog
    title="规则测试"
    :close-on-click-modal="false"
    width="70%"
    :visible.sync="visible">
    <el-row>
      <el-row style="margin-bottom: 10px;" v-for="(entity,index) in entityInfos">
        <el-col :span="4">
          {{entity.entityIdentify}}-- {{entity.entityName}}
        </el-col>
        <el-table
          :data="entity.itemInfos"
          border
          height="250"
          style="width: 100%">
          <el-table-column
            prop="itemName"
            label="属性名称"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="itemIdentify"
            label="属性标识"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="itemValue"
            label="属性值"
           >
            <template slot-scope="scope">
              <el-input v-model="scope.row.itemValue"  placeholder="属性值"></el-input>
            </template>
          </el-table-column>
        </el-table>
      </el-row>

      <el-row style="padding-bottom: 20px;">
        <el-button type="primary" @click="dataFormSubmit()">测试</el-button>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>规则</span>
            </div>
            <div  class="temp_content">
              <pre>
                {{template}}
              </pre>
            </div>
          </el-card>

        </el-col>
        <el-col :span="12">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>结果</span>
            </div>
            <div  class="temp_content">
              <pre>
                {{resultStr}}
              </pre>
            </div>
          </el-card>
        </el-col>

      </el-row>
    </el-row>


    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">返回</el-button>

    </span>


  </el-dialog>


</template>

<script>
  import { getInfo, testRule } from '@/api/scene'
  import { entityItemList } from '@/api/entity'

  export default {
    data () {
      return {
        resultStr: '',
        template: '',
        entity: null,
        entityItem: null,
        sceneId: null,
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

      init (id) {
        this.sceneId = id
        this.visible = true

        if (this.sceneId) {
          let params = {
            id: this.sceneId + ''
          }
          getInfo(params).then(res => {
            if (res.data.data && res.data.resultCode === 0) {
              this.entityInfos = res.data.data.entitys
              console.log(this.entityInfos)
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
          entity: '',
          entityItem: '',
          entityItemValue: ''
        }
        this.conditionList.push(item)
      },

      // 表单提交
      dataFormSubmit () {
        let params = {
          id: this.sceneId + '',
          entitys: JSON.stringify(this.entityInfos)
        }
        testRule(params).then(res => {
          if (res.data && res.data.resultCode === 0) {
            this.resultStr = res.data.data.result
            this.template = res.data.data.template
          } else {
            this.$message.error(res.data.resultMsg)
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  .temp_content{
    height: 400px;
    overflow-y: scroll;
  }
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
