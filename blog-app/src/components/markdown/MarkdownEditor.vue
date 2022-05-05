<template>
  <mavon-editor
    class="me-editor"
    ref="md"
    v-model="editor.value"
    @imgAdd="imgAdd"
    v-bind="editor">
  </mavon-editor>
</template>


<script>

import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import {upload} from '@/api/upload'

export default {
  name: 'MarkdownEditor',
  props: {
    editor: Object
  },
  data() {
    return {}
  },
  mounted() {
    this.$set(this.editor, 'ref', this.$refs.md)
    // this.onAddUrl()
  },
  created() {

  },
  methods: {
    imgAdd(pos, $file) {
      let that = this
      let formdata = new FormData();
      formdata.append('image', $file);

      upload(formdata).then(data => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        if (data.success) {

          that.$refs.md.$img2Url(pos, data.data);
        } else {
          that.$message({message: data.msg, type: 'error', showClose: true})
        }

      }).catch(err => {
        that.$message({message: err, type: 'error', showClose: true});
      })
    },
    onAddUrl() {
      console.log(1)
      this.$nextTick(function () {
        let _aList = document.querySelectorAll(".v-note-navigation-content a");
        for (let i = 0; i < _aList.length; i++) {
          let _aParent = _aList[i].parentNode;
          let _a = _aParent.firstChild;
          if (!_a.id) continue; // 把不属于导航中的a标签去掉，否则会报错
          let _text = _aParent.lastChild;
          let text = _text.textContent;
          _a.href = "#" + _a.id;
          _a.innerText = text;
          _aParent.removeChild(_text);
          // _a.style.color = "red";
        }
      });
    },
  },
  watch: {
    editor(){
      this.onAddUrl()
    }
  },
  components: {
    mavonEditor
  }
}
</script>
<style>

.me-editor {
  z-index: 6 !important;
}

.v-note-wrapper.fullscreen {
  top: 60px !important
}

.v-note-navigation-wrapper {
  position: fixed !important;
  right: 0 !important;
}
</style>
