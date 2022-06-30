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
          prop="tid"
          label="类型编号"
          sortable>
      </el-table-column>
      <el-table-column
          prop="tname"
          label="类型名">
      </el-table-column>
      <el-table-column
          prop="tlimit"
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
              @confirm="handleDelete(scope.row)" >
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
          <el-form-item label="类型名">
            <el-input v-model="form.tname" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio v-model="form.tlimit" label=0>禁用</el-radio>
            <el-radio v-model="form.tlimit" label=1>启用</el-radio>
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
  name: "product_type",
  data(){
    return{
      tableData:[],
      dialogVisible:false,
      search:'',
      currentPage4:1,
      pageSize:10,
      total:10,
      form:{
        tid:'',
        tlimit:1,
      }
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/type/searchAll",{
        params:{
          pageNum:this.currentPage4,
          pageSize:this.pageSize,
          search:this.search,
        }
      }).then(res =>{
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    add(){
      this.dialogVisible=true
      this.form={}

    },
    handleExit(row){
      this.form=JSON.parse(JSON.stringify(row)) //深拷贝
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
    handleDelete(row){
      request.delete("/type/deleteType",{
        params:{
          tname:row.tname
        }
          }
      ).then(res =>{
        if(res.code==='0')
        {
          this.$message({
            type:"success",
            message:"删除类型成功"
          })
        }
        else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()
      })
    },
    save(){
      if(this.form.tid){
        request.put("/type/updateType",this.form).then(res =>{
          if(res.code==='0')
          {
            this.$message({
              type:"success",
              message:"修改类型成功"
            })
          }
          else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
          this.dialogVisible=false
        })

      }
      else{
        request.post("/type/addType",this.form).then(res =>{
          if(res.code==='0')
          {
            this.$message({
              type:"success",
              message:"添加类型成功"
            })
          }
          else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
          this.dialogVisible=false
        })
      }
    },
  }
}
</script>

<style scoped>

</style>