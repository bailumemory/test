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
          prop="productId"
          label="商品编号"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="pname"
          label="商品名">
      </el-table-column>
      <el-table-column
          prop="pnum"
          label="商品数">
      </el-table-column>
      <el-table-column
          prop="valuse"
          label="单价">
      </el-table-column>
      <el-table-column
          prop="type"
          label="商品类型">
      </el-table-column>
      <el-table-column
          prop="path"
          label="图片">
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
              @confirm="handleDelete(scope.row.productId)" >
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

      <el-dialog title="提示" :visible.sync="dialogVisible" width="40%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="商品名">
            <el-input v-model="form.pname" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="数量">
            <el-input v-model="form.pnum" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="单价">
            <el-input v-model="form.valuse" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="类型">
            <el-select v-model="form.type" filterable placeholder="请选择" @click="loadType">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.tname"
                  :value="item.tname">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="图片">
            <el-input v-model="form.path" style="width:80%"></el-input>
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
  name: "Product",
  data(){
    return {
      form:{
        pname:'',
        pnum:'',
        valuse:'',
        productId:'',
      },
      tname:'',
      dialogVisible:false,
      search:'',
      currentPage4:1,
      pageSize:10,
      total:10,
      tableData: [],
      options:[],
    }
  },
  created() {
    this.load()
    this.loadType()
  },
  methods:{

    loadType(){
      request.get("/type/searchType",{
        params:{
          limit:1
        }
      }).then(res =>{
        this.options=res.data
      })
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
    handleDelete(productId){
      request.delete("/products/deleteProduct",{
        params:{
          productId:productId,
        }
      }).then(res => {
        if(res.code==='0')
        {
          this.$message({
            type:"success",
            message:"删除商品成功"
          })
        }
        else{
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()//刷新页面
        this.loadType()
      })

    },
    load(){
      request.get("/products/searchProducts2",{
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
    save(){
      if(this.form.productId){
        request.put("/products/updateProduct",this.form).then(res =>{
          if(res.code==='0')
          {
            this.$message({
              type:"success",
              message:"修改商品成功"
            })
          }
          else{
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
          this.loadType()
          this.dialogVisible=false
        })

      }
      else{
        request.post("/products/addProduct",this.form).then(res =>{
          if(res.code==='0')
          {
            this.$message({
              type:"success",
              message:"添加商品成功"
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
          this.loadType()
        })
      }

    },
    add(){
      this.dialogVisible=true
      this.form={}
    },
  }
}
</script>

<style scoped>

</style>