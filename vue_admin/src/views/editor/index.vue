<template>
    <el-container>
        <div id="minimap"></div>       <!-- 缩略图 DOM 结构规约参考 Minimap API -->

        <div id="toolbar">
            <svg-icon icon-class="file"/>
        </div>       <!-- 工具栏 DOM 结构规约参考 Toolbar API --> 

        <div id="itempannel">
            <div class="getItem" data-type="node" data-shape="circle">圆形</div>    
        </div>    <!-- 元素面板栏 DOM 结构规约参考 Itempannel API -->

        <div id="detailpannel"></div>  <!-- 详情面板栏 DOM 结构规约参考 Detailpannel API -->

        <div id="contextmenu"></div>   <!-- 右键菜单栏 DOM 结构规约参考 Contextmenu API -->

        <div id="page"></div>          <!-- 参考 Flow、Mind API -->
    </el-container>
</template>

<script>
import G6Editor from '@antv/g6-editor'

export default {
    name: 'Editor',
    components: {},
    mounted() {
        this.initG6Editor()
    },
    props: {

    },
    data() {
        return {

        }
    },
    methods: {
        initG6Editor() {
            self.editor = new G6Editor()
            const minimap = new G6Editor.Minimap({
                container: 'minimap',
            });
            const toolbar = new G6Editor.Toolbar({
                container: 'toolbar',
            });
            const contextmenu = new G6Editor.Contextmenu({
                container: 'contextmenu',
            });
            const itempannel = new G6Editor.Itempannel({
                container: 'itempannel',
            });
            const detailpannel = new G6Editor.Detailpannel({
                container: 'detailpannel',
            });
            const page = new G6Editor.Flow({
                graph: {
                    container: 'page',
                },
            });
            self.editor.add(minimap);
            self.editor.add(toolbar);
            self.editor.add(contextmenu);
            self.editor.add(itempannel);
            self.editor.add(detailpannel);
            self.editor.add(page);
        }
    }

}
</script>

<style>

</style>
