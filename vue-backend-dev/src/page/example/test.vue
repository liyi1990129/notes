<template>
<!-- 核保处理-列表页面 -->
    <div class="fillcontain" height="800vh">
        <el-form label-width="100px" class="search_container">
            <el-row class="mt10">
                <el-col :span="6">
                    <el-form-item label="受理号：" prop="caseno">
                        <el-input v-model="searchParams.caseno" @blur="chearTrim" placeholder="受理号" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="保单号：" prop="caseno">
                        <el-input v-model="searchParams.chdrnum" @blur="chearTrim" placeholder="保单号" clearable></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12" class="pl20">
                    <el-button type="primary" icon="el-icon-search" @click="goHeBao"></el-button>
                    <el-button type="primary" plain  icon="el-icon-refresh" @click="resetSearch"></el-button>
                </el-col>
            </el-row>
        </el-form>
        <div class="contain">
          <div class="table_container">
          </div>
        </div>
    </div>
</template>

<script>
  
    export default {
        data(){
            return {
                searchParams:{
                    caseno:'',
                    chdrnum:''
                },
                loading:false,
             
            }
        },
        created(){
        },
        mounted(){
           
        },
        methods: {
          
          keyEvent() {
            var e = event || window.event;
            if (e && e.keyCode == 113) {
              
            }
          },
        },
        computed:{
          
        },

      activated() {
     

        //监听F2 按键事件
        document.addEventListener('keydown', this.keyEvent);
      },
      beforeRouteLeave(to, from, next) {
        document.removeEventListener('keydown', this.keyEvent)
        next();
      },
    }
</script>

<style lang="less" scoped>
    .fillcontain{
        padding-bottom: 0;
    }
    .contain{
        background: #fff;
        padding: 10px;
        margin-bottom: 20px;
    }
   .pagination{
       padding: 10px 20px;
       text-align: right;
   }
   .operationIcon{
        // padding: 12px;
        border:1px solid #e5e5e5;
        border-radius: 50%;
        width: 22px; 
        height: 22px;
        display: inline-block;
        cursor: pointer;
        box-shadow:0 0 3px #f9f9f9;
        .svg-icon{
            font-size: 14px;
            fill:#68a3fc;
        }
   }
   .search_container{
       padding: 20px 20px 0 20px;
   }
</style>



