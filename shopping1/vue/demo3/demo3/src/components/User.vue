<template>
  <div style="padding:10px;margin-top: 50px;margin-left: 200px">
    <div style="margin:10px 0px">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <div style="margin:10px 0px">
      <el-input
          style="width:20%"
          placeholder="请输入关键字"
          v-model="search"
          clearable>
      </el-input>
      <el-button type="primary" style="margin-left: 10px" @click="load">搜索</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="id"
          label="编号"
          sortable>
      </el-table-column>
      <el-table-column
          prop="username"
          label="姓名">
      </el-table-column>
      <el-table-column
          prop="password"
          label="密码">
      </el-table-column>
      <el-table-column
          prop="sex"
          label="性别">
      </el-table-column>
      <el-table-column
          prop="degrees"
          label="角色">
      </el-table-column>
      <el-table-column
          prop="moneys"
          label="余额">
      </el-table-column>
      <el-table-column
          prop="limits"
          label="状态">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-button @click="handleExit(scope.row)" type="text">编辑</el-button>
          <el-popconfirm
              style="margin-left:10px"
              title="确定删除吗？"
              @confirm="handleDelete(scope.row.id)" >
            <el-button slot="reference" type="text" >删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.username" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="角色">
            <el-select v-model="form.degrees" placeholder="请选择" @click="loadDegree">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio v-model="form.limits" label='禁用'>禁用</el-radio>
            <el-radio v-model="form.limits" label='启用'>启用</el-radio>
          </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
           <el-button @click="dialogVisible = false">取 消</el-button>
           <el-button type="primary" @click="save">确 定</el-button>
       </span>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data(){
    return {
      form:{
        degrees:'',
        limits:'',
        sex:''
      },
      dialogVisible:false,
      search:'',
      currentPage4:1,
      pageSize:10,
      total:10,
      tableData: [],
      options:''
    }
  },
  created() {  //页面加载就显示
    this.load()
  },
  methods:{
    loadDegree(){
      if(this.$cookies.get('user').degrees==="营销经理"){
        this.options=[{
          value:'普通用户',
          label:'普通用户'
        }]
      }
      else{
        this.options=[{
          value:'营销经理',
          label:'营销经理'
        },
          {
            value:'商品专员',
            label:'商品专员'
          },
          {
            value:'普通用户',
            label:'普通用户'
          },
        ]
      }
    },
    load(){
      request.get("/user/searchUser",{
        params:{
          pageNum:this.currentPage4,
          pageSize:this.pageSize,
          search:this.search,
          degrees:this.$cookies.get('user').degrees
        }

      }).then(res => {
        this.tableData=res.data.records
        this.total=res.data.total
        this.loadDegree()

      })
    },
    handleExit(row){
      this.form=JSON.parse(JSON.stringify(row)) //深拷贝含有id信息
      this.dialogVisible=true
    },
    handleSizeChange(pageSize){ //改变当前页面显示个数时触发
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){   //改变页数时触法
      this.currentPage4=pageNum
      this.load()
    },
    handleDelete(id){
      request.delete("/user/deleteUser/"+id).then(res => {
        if(res.code==='0')
        {
          this.$message({
            type:"success",
            message:"删除用户成功"
          })
        }
        else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()//刷新页面
      })

    },
    add(){
      this.dialogVisible=true
      this.form={}  //清空表单内容
    },
    save(){
      //判断是更新
      if(this.form.id){
        request.put("/user/updateUser",this.form).then(res => {
          //跟新成功提示
          if(res.code==='0')
          {
            this.$message({
              type:"success",
              message:"跟新用户成功"
            })
          }
          else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()//刷新页面
          this.dialogVisible=false //关闭弹窗
        })
      }
      else{
        request.post("/user",this.form).then(res => {
          if(res.code==='0')
          {
            this.$message({
              type:"success",
              message:"添加用户成功"
            })
          }
          else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()//刷新页面
          this.dialogVisible=false //关闭弹窗
        })
      }
    }
  }
}
</script>

<style scoped>

</style>