<template>
  <div style="padding:10px;margin-top: 50px;margin-left: 200px">
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="oid"
          label="订单号"
          sortable>
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名">
      </el-table-column>
      <el-table-column
          prop="pname"
          label="商品名">
      </el-table-column>
      <el-table-column
          prop="nums"
          label="数量">
      </el-table-column>
      <el-table-column
          prop="totals"
          label="总价">
      </el-table-column>
      <el-table-column
          prop="times"
          label="购买时间">
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
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Order",
  data(){
    return{
      currentPage4:1,
      pageSize:10,
      total:10,
      tableData: [],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/order/getOrder",{
      }).then(res =>{
        this.tableData=res.data
      })
    },
    handleSizeChange(pageSize){ //改变当前页面显示个数时触发
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){   //改变页数时触法
      this.currentPage4=pageNum
      this.load()
    },

  },
}
</script>

<style scoped>

</style>