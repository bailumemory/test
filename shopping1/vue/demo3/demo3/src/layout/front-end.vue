<template>
  <div>
    <Daohangs></Daohangs>
    <div style="display:flex;">
      <left_lable></left_lable>
      <router-view style="flex:1"></router-view>
    </div>
  </div>

</template>

<script>

import left_lable from "@/components/left_lable";
import Daohangs from "@/components/Daohangs";
export default {
  name: "front-end",
  components:{
    Daohangs,
    left_lable,
  }
}
</script>

<style scoped>

</style>