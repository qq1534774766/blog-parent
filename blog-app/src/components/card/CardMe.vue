<template>
  <el-card>
    <h1 class="me-author-name">阿果</h1>
    <div class="me-author-description">
      <span><i class="iconfont icon-guangdong"></i> 广东&广州</span>&nbsp;
      <span><i class="iconfont icon-java"></i>java上帝架构师</span>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(qq)" :title="qq.title" class="iconfont icon-QQ"></i>
      <a style="font-size: 30px" :title="bilibili.title" href="https://github.com/qq1534774766/blog-parent" class="iconfont icon-github-fill"></a>
      <i   ></i>
    </div>
  </el-card>

</template>

<script>
  export default {
    name: 'CardMe',
    data() {
      return {
        qq: {title: 'QQ', message: '1534774766'},
        bilibili: {
          title: 'GitHub',
          message: '<a target="_blank" href="https://github.com/qq1534774766/blog-parent">https://github.com/qq1534774766/blog-parent</a>'
        }
      }
    },
    methods: {
      showTool(tool) {
        this.$message({
          duration: 0,
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: '<strong>' + tool.message + '</strong>'
        });
      }
    }
  }
</script>

<style scoped>
.iconfont{
  font-size: 20px;
}
  .me-author-name {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #5FB878;
  }

  .me-author-description {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
