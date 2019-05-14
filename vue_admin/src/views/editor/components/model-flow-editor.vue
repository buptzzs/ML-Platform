<template>
    <div id="editor">
        <toolbar
            @save="saveData"
            @change-eage="changeEage"/>
        <div :style="{height:'50px'}"/>
        <div class="bottom-container">
            <context-menu />
            <div id="itempannel">
                    <el-tree
                    :data="tree_data"
                    node-key="type"
                    :default-expanded-keys="['DataPreprocess']"
                    :expand-on-click-node="false">
                    <span class="custom-tree-node" slot-scope="{ node, data }">
                        <span v-if="!node.isLeaf">{{ node.label }}</span>
                        <span>
                            <li v-if="node.isLeaf"
                                class="getItem"
                                :data-shape="data.type"
                                data-type="node"
                                data-size="170*34">
                                <span class="pannel-type-icon"/>{{data.label}}
                            </li>   
                        </span>
                    </span>
                    </el-tree>
            </div>
            <div id="detailpannel">
                <div
                    id="node_detailpannel"
                    data-status="node-selected"
                    class="pannel">
                    <div class="pannel-title">模型详情: {{selectedModel.shape}}
                    </div>
                    <div class="block-container">
                        <div v-if="selectedModel && selectedModel.type === 'node'" >
                            <div >
                                <el-form label-width="100px">
                                    <el-form-item
                                        v-for="param in inputingParams"
                                        v-bind:key="param.name"
                                        :label="param.name"
                                        >
                                        <div v-if="param.type=='bool'">
                                             <el-radio-group v-model="param.value">
                                                <el-radio  label='true'/>
                                                <el-radio label='false'/>
                                              </el-radio-group>
                                        </div>
                                        <div v-else-if="param.type=='model'">
                                            <el-select v-model="param.value" placeholder="请选择">
                                                <el-option
                                                    v-for="file in model_files"
                                                    :key="file.name"
                                                    :label="file.name"
                                                    :value="file.name"
                                                >
                                                </el-option>
                                            </el-select>
                                        </div>
                                        <div v-else-if="param.type=='option'">
                                            <el-select v-model="param.value" placeholder="请选择">
                                                <el-option
                                                    v-for="option in param.options"
                                                    :key="option"
                                                    :label="option"
                                                    :value="option"
                                                >
                                                </el-option>
                                            </el-select>
                                        </div>                                        
                                        <div v-else>
                                            <el-input v-model="param.value" :placeholder="param.tip"/>
                                        </div>                                        
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                    </div>
                </div>
                <div
                    id="canvas_detailpannel"
                    data-status="canvas-selected"
                    class="pannel">
                    <div class="pannel-title">画布</div>
                    <div class="block-container">
                        <el-checkbox @change="toggleGrid">网格对齐</el-checkbox>
                    </div>
                </div>
            </div>
            <navigator
                :cur-zoom="curZoom"
                :min-zoom="minZoom"
                :max-zoom="maxZoom"
                @change-zoom="changeZoom" />
            <page />
        </div>
    </div>
</template>

<script>
import Navigator from './navigator';
import Toolbar from './toolbar';
import ContextMenu from './context-menu';
import Page from './page';
import Editor from './editor';
import './register-items.js';
import {saveTask, getTask} from '@/api/userTask'
import {getFileInfos} from '@/api/file'


export default {
    components: {
        Navigator,
        Toolbar,
        ContextMenu,
        Page
    },
    extends: Editor,
    data() {
        return {
            temp: 'base-flow-editor',
            tempInputingLabel: '',
            tempColor: '',
            tempParams: null,
            data : {},
            model_files:[],
            tree_data: [
                {
                    label: '数据预处理',
                    type:'DataPreprocess',
                    children: [
                        {
                            label: '数据格式转换',
                            type:'FileComponent',
                            isLeaf: true
                        },
                       {
                            label: '缺失值填充',
                            type:'Impute',
                            isLeaf: true
                        },{
                            label: '列删除',
                            type:'DeleteColumn',
                            isLeaf: true                            
                        },{
                            label: '数据集生成',
                            type:'DatasetGenerate',
                            isLeaf: true                                
                        },
                        {
                            label: 'Play',
                            type:'Play',
                            isLeaf: true                                
                        },{
                            label: '数据文件预览',
                            type: 'Preview',
                            isLeaf: true
                        }
                    ]
                }, {
                    label: '特征处理',
                    type:'FeaturePreprocess',    
                    children:[
                        {
                            label: '特征正则化',
                            type:'Normalize',
                            isLeaf: true                                
                        },
                    ]                
                },{
                    label: '分类算法',
                    type: 'Classification',
                    children:[
                        {
                            label:'逻辑回归',
                            type:'LogisticRegression',
                            isLeaf:true
                        },
                        {
                            label:'支持向量机',
                            type:'SupportVectorMachine',
                            isLeaf:true                            
                        },{
                            label:'朴素贝叶斯',
                            type:'Bayes',
                            isLeaf:true                               
                        },{
                            label:'随机深林',
                            type:'random-forest',
                            isLeaf:true                                  
                        },
                        {
                            label:'K最近邻',
                            type:'KNearestNeighbor',
                            isLeaf:true
                        }                        
                    ]
                }, {
                    label: '聚类算法',
                    type:'Cluster',
                    children:[
                        {
                            label:'k均值聚类',
                            type:'k-means',
                            isLeaf:true
                        }
                    ]
                },{
                    label:'回归算法',
                    type:'Regression',
                    children:[
                        {
                            label:'支持向量机回归',
                            type:'SupportVectorRegression',
                            isLeaf:true                            
                        },
                        {
                            label:'决策树',
                            type:'DecisionTree',
                            isLeaf:true                            
                        }                        
                    ]                    
                },{
                    label: '评估',
                    type:'Estimate',
                    children:[
                        {
                            label:'二分类评估',
                            type:'BiClassificationEval',
                            isLeaf:true                            
                        },
                     {
                            label:'多分类评估',
                            type:'ClassificationEval',
                            isLeaf:true                            
                        }                        
                    ]                    
                }
                ]            
            };
    },
    computed: {
        inputingLabel: {
            get() {
                return this.tempInputingLabel !== null ? this.tempInputingLabel : this.selectedModel.label;
            },
            set(value) {
                this.updateGraph('label', value);
                this.tempInputingLabel = null;
            }
        },
        inputingParams: {
            get() {
                // return this.tempParams != null ? this.tempParams : this.selectedModel.params
                return this.selectedModel.params
            },
            set(value) {
                this.updateGraph('params', value)
                this.tempParams = null
            }
        },
        color: {
            get() {
                return this.tempColor !== null ? this.tempColor : this.selectedModel.color;
            },
            set(value) {
                this.updateGraph('color', value);
                this.tempColor = null;
            }
        }
    },
    mounted() {
        const page = this.page;
    
        page.changeAddEdgeModel({
            shape: 'line'
        });
        // 输入锚点不可以连出边
        page.on('hoveranchor:beforeaddedge', ev => {
            if (ev.anchor.type === 'input') {
                ev.cancel = true;
            }
        });
        page.on('dragedge:beforeshowanchor', ev => {
        // 只允许目标锚点是输入，源锚点是输出，才能连接
            if (!(ev.targetAnchor.type === 'input' && ev.sourceAnchor.type === 'output')) {
                ev.cancel = true;
            }
            // 如果拖动的是目标方向，则取消显示目标节点中已被连过的锚点
            if (ev.dragEndPointType === 'target' && page.anchorHasBeenLinked(ev.target, ev.targetAnchor)) {
                ev.cancel = true;
            }
            // 如果拖动的是源方向，则取消显示源节点中已被连过的锚点
            if (ev.dragEndPointType === 'source' && page.anchorHasBeenLinked(ev.source, ev.sourceAnchor)) {
                ev.cancel = true;
            }
        });
        this.getUserTaskInfo();
        this.fetchModelData()

    },
    methods: {
        saveData(taskname, beginFile, endFile) {
            const page_data = JSON.stringify(this.page.save())
            console.log(beginFile, endFile)
            const params = {
                username: this.$store.getters.name,
                taskname: taskname,
                beginFile: beginFile,
                endFile: endFile,
                jsonInfo: page_data
            };
            console.log(params)
            saveTask(params).then(response => {
                this.$message("保存成功");
            })
        },
        changeEage(type) {
            this.page.changeAddEdgeModel({
                shape: type
            });
        },
        getUserTaskInfo() {
            const taskname = this.$store.getters.taskname;
            if(taskname.length == 0){
                console.log("空任务")
                this.$message("空任务");
                this.data = {}
            }else{
                const params = {
                        username: this.$store.getters.name,
                        taskname: taskname
                }
                getTask(params).then(response =>{
                    const task_info = JSON.parse(response.data)
                    const s_data = task_info.data
                    console.log(task_info)
                    this.data = JSON.parse(s_data)
                    this.page.read(this.data)
                })
            }
        },
        fetchModelData() {
            const params = {
                username: this.$store.getters.name,
                type: 'model'
            }
            getFileInfos(params).then(response =>{
                this.model_files = response.data
                console.log(this.model_files)
            })
        },        




    }
};
</script>
<style lang="scss">

#itempannel{
  height: 100%;
  position: absolute;
  left: 0px;
  z-index: 2;
  background: #F7F9FB;
  width: 200px;
  padding-top: 8px;
  border-right: 1px solid #E6E9ED;
  text-align: left;
}

#itempannel li{
  color: rgba(0,0,0,0.65);
  border-radius: 4px;
  width: 160px;
  height: 28px;
  line-height: 26px;
  padding-left: 8px;
  border: 1px solid rgba(0,0,0,0);
  list-style-type: none;
}

#itempannel .pannel-type-icon{
  width: 16px;
  height: 16px;
  display: inline-block;
  vertical-align: middle;
  margin-right: 8px;
  background: url(https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg)
}

#itempannel li:hover{
  background: white;
  border: 1px solid #CED4D9;
  cursor: move;
}

#detailpannel{
  height: 100%;
  position: absolute;
  right: 0px;
  z-index: 2;
  background: #F7F9FB;
  width: 300px;
  border-left: 1px solid #E6E9ED;
}
#detailpannel .pannel{
  display: none
}
#detailpannel .block-containe{
  padding-top: 20px;
}
#detailpannel .input{
  margin-left: 16px;
}
#detailpannel .name-input{
  width: 120px;
}
#detailpannel .width-input{
  width: 52px;
}
#detailpannel .height-input{
  width: 52px;
}
#detailpannel .block-container{
  padding: 16px 8px;
  text-align: left;
}
.bottom-container{
  position: relative;
}
.pannel-title{
  height: 32px;
  border-top: 1px solid #DCE3E8;
  border-bottom: 1px solid #DCE3E8;
  background: #EBEEF2;
  color: #000;
  line-height: 28px;
  padding-left: 12px;
}
.color-picker{
  vertical-align: middle;
  margin-left: 16px;
}
.p{
  margin-bottom: 12px;
}
/* .model-card{
  padding: 8px 14px;
  padding-right: 8px;
  font-size: 12px;
}
.left-bar{
  position: absolute;
  width: 4px;
  height: 100%;
  top: 0px;
  left: 0px;
  border-radius: 4px 0px 0px 4px;
}
.type-icon {
  width: 20px;
  height: 16px;
  margin-top: -8px;
  display: inline-block;
  position: absolute;
  top: 50%;
}
.state-icon {
  width: 16px;
  height: 16px;
  margin-top: -8px;
  margin-left: 4px;
  position: absolute;
  top: 50%;
  right: 8px;
  display: inline-block;
}
.content-list {
  display: inline-block;
  min-width: 128px;
  margin: 0;
  margin-left: 20px;
  padding-left: 12px;
  list-style: none;
  color: rgba(0,0,0,0.45);
}
.model-card .title{
  color: #000000;
} */
</style>