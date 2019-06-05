import factory from '../../../assets/factory.svg';
import G6Editor from '@antv/g6-editor'

const Flow = G6Editor.Flow;

// 注册模型卡片基类
Flow.registerNode('model-card', {
    draw(item) {
        const group = item.getGraphicGroup();
        const model = item.getModel();
        const width = 184;
        const height = 40;
        const x = -width / 2;
        const y = -height / 2;
        const borderRadius = 4;
        const keyShape = group.addShape('rect', {
            attrs: {
                x,
                y,
                width,
                height,
                radius: borderRadius,
                fill: 'white',
                stroke: '#CED4D9'
            }
        });
        // 左侧色条
        group.addShape('path', {
            attrs: {
                path: [
                    [ 'M', x, y + borderRadius ],
                    [ 'L', x, y + height - borderRadius ],
                    [ 'A', borderRadius, borderRadius, 0, 0, 0, x + borderRadius, y + height ],
                    [ 'L', x + borderRadius, y ],
                    [ 'A', borderRadius, borderRadius, 0, 0, 0, x, y + borderRadius ]
                ],
                fill: this.color_type
            }
        });
        // 类型 logo
        group.addShape('image', {
            attrs: {
                img: this.type_icon_url,
                x: x + 16,
                y: y + 12,
                width: 20,
                height: 16
            }
        });
        // 名称文本
        const label = model.label ? model.label : this.label;
        group.addShape('text', {
            attrs: {
                text: label,
                x: x + 52,
                y: y + 13,
                textAlign: 'start',
                textBaseline: 'top',
                fill: 'rgba(0,0,0,0.65)'
            }
        });
        // 状态 logo
        group.addShape('image', {
            attrs: {
                img: this.state_icon_url,
                x: x + 158,
                y: y + 12,
                width: 16,
                height: 16
            }
        });
        return keyShape;
    },
    // 设置锚点
    anchor: [
        [ 0.5, 0 ], // 上面边的中点
        [ 0.5, 1 ] // 下边边的中点
    ]
});

// k 均值聚类
Flow.registerNode('k-means', {
    label: 'k均值聚类',
    color_type: '#1890FF',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
        }], // 上面边的中点
        [ 0.5, 1, {
            type: 'output'
        }] // 下边边的中点
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
<<<<<<< HEAD
=======
        }, {
            name: 'n_clusters',
            value: '0',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        }, {
            name: 'model_name',
            value: 'kmeans_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
            name: 'n_clusters',
            value: '0',
            type:'str'
        },{
            name: 'n_int',
            value: '10',
            type:'str'
        },{
            name: 'max_iter',
            value: '300',
            type:'str'
        },
    ]
}, 'model-card');

<<<<<<< HEAD
Flow.registerNode('MeanShift', {
    label: '均值漂移',
    color_type: '#1890FF',
=======
Flow.registerNode('Play', {
    label: 'Play',
    color_type: '#1890FF',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }], // 上面边的中点
        [0.5, 1, {
            type: 'output'
        }] // 下边边的中点
    ],
    params: [
        {
            name: 'time',
            value: '60',
            type: 'str'
        },
    ]
}, 'model-card');

// 随机森林
Flow.registerNode('random-forest', {
    label: '随机森林',
    color_type: '#9254DE',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
        }], // 上面边的中点
        [ 0.5, 1, {
            type: 'output'
        }] // 下边边的中点
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
<<<<<<< HEAD
        }, {
            name: 'model_name',
            value: 'kmeans_test',
=======
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'random_forest_test',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
<<<<<<< HEAD
            name: 'bin_seeding',
            value: 'false',
            type:'bool'
        },{
            name: 'min_bin_freq',
            value: '1',
            type:'str'
        },{
            name: 'cluster_all',
            value: 'true',
            type:'bool'
        },
    ]
}, 'model-card');
Flow.registerNode('SpectralClustering', {
    label: '谱聚类',
    color_type: '#1890FF',
=======
            name: 'n_estimators',
            value: '10',
            type:'str'
        },{
            name: 'min_samples_split',
            value: '2',
            type:'str'
        },{
            name: 'min_samples_leaf',
            value: '1',
            type:'str'
        },
    ]
}, 'model-card');

Flow.registerNode('random-forest-re', {
    label: '随机森林',
    color_type: '#9254DE',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
<<<<<<< HEAD
        }], // 上面边的中点
=======
        }],
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        [ 0.5, 1, {
            type: 'output'
        }] // 下边边的中点
    ],

    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
<<<<<<< HEAD
        }, {
            name: 'model_name',
            value: 'kmeans_test',
=======
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'random_forest_re_test',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
<<<<<<< HEAD
            name: 'n_clusters',
            value: '8',
            type:'str'
        },{
            name: 'n_int',
            value: '10',
            type:'str'
        },{
            name: 'gamma',
            value: '1.0',
=======
            name: 'n_estimators',
            value: '10',
            type:'str'
        },{
            name: 'min_samples_split',
            value: '2',
            type:'str'
        },{
            name: 'min_samples_leaf',
            value: '1',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'str'
        },
    ]
}, 'model-card');

Flow.registerNode('GaussianMixture', {
    label: '高斯混合模型',
    color_type: '#1890FF',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
        }], // 上面边的中点
        [ 0.5, 1, {
            type: 'output'
        }] // 下边边的中点
    ],
    params: [
        {
            name: 'train',
            value: 'false',
<<<<<<< HEAD
            type:'bool'
        }, {
            name: 'model_name',
            value: 'kmeans_test',
=======
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str',
        }, {
            name: 'model_name',
            value: 'Bayes_test',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
<<<<<<< HEAD
            name: 'n_components',
            value: '1',
            type:'str'
        },{
            name: 'n_int',
            value: '1',
            type:'str'
        },{
            name: 'max_iter',
            value: '100',
            type:'str'
        },
    ]
}, 'model-card');

Flow.registerNode('Brich', {
    label: 'Brich聚类',
    color_type: '#1890FF',
=======
            name: 'alpha',
            value: '1.0',
            type:'str'
        },{
            name: 'fit_prior',
            value: 'true',
            type:'bool'
        },{
            name: 'norm',
            value: 'false',
            type:'bool'
        },

    ]
}, 'model-card');
Flow.registerNode('BayesianRidge', {
    label: '贝叶斯线性回归',
    color_type: '#6495ED',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/uZVdwjJGqDooqKLKtvGA.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
        }],
        [ 0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str',
        }, {
            name: 'model_name',
            value: 'BayesianRidge_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
            name: 'n_iter',
            value: '300',
            type:'str'
        },{
            name: 'compute_score',
            value: 'false',
            type:'bool'
        },
    ]
}, 'model-card');
// 读数据表
Flow.registerNode('FileComponent', {
    label: '数据格式转换',
    color_type: '#FAAD14',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
        }], // 上面边的中点
        [ 0.5, 1, {
            type: 'output'
<<<<<<< HEAD
        }] // 下边边的中点
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
        }, {
            name: 'model_name',
            value: 'kmeans_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
            name: 'threshold',
            value: '0.5',
            type:'str'
        },{
            name: 'branching_factor',
            value: '50',
            type:'str'
        },{
            name: 'n_clusters',
            value: '3',
            type:'str'
=======
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'inType',
            value: 'csv',
            options: ['csv','json','txt'],
            type:'option'
        }, {
            name: 'outType',
            value: 'json',
            options: ['csv', 'json', 'txt'],
            type:'option'
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        },
    ]
}, 'model-card');

<<<<<<< HEAD
Flow.registerNode('Play', {
    label: 'Play',
    color_type: '#1890FF',
=======
Flow.registerNode('Preview', {
    label: '数据文件预览',
    color_type: '#FAAD14',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
    ]
}, 'model-card');

Flow.registerNode('SupportVectorMachine', {
    label: '支持向量机',
    color_type: '#FF0000',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
<<<<<<< HEAD
        [0.5, 0, {
            type: 'input'
        }], // 上面边的中点
        [0.5, 1, {
            type: 'output'
        }] // 下边边的中点
    ],
    params: [
        {
            name: 'time',
            value: '60',
            type: 'str'
=======
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'SVM_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },
        {
            name: 'kernel',
            value: 'rbf',
            type:'str'
        }, {
            name: 'C',
            value: '1.0',
            type:'str'
        },
        {
            name: 'coef0',
            value: '0.0',
            type:'str'
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        },
    ]
}, 'model-card');

// 随机森林
Flow.registerNode('RandomForestClassifier', {
    label: '随机森林分类器',
    color_type: '#9254DE',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
<<<<<<< HEAD
        [ 0.5, 0, {
            type: 'input'
        }],
=======
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],

    params: [
        {
            name: 'train',
<<<<<<< HEAD
            value: 'true',
=======
            value: 'false',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'bool'
        }, {
            name: 'ratio',
            value: '0.2',
<<<<<<< HEAD
            type:'str'
        }, {
            name: 'model_name',
            value: 'random_forest_test',
=======
            type: 'str'
        }, {
            name: 'model_name',
            value: 'SVR_test',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'str'
        },
        {
            name: 'model_path',
            value: '',
<<<<<<< HEAD
            type:'model'
        },
        {
            name:'max_depth',
            value:'2',
            type:'str'
        },
        {
            name: 'bootstrap',
            value: 'true',
            type: 'bool'
        },
        {
            name: 'n_estimators',
            value: '1',
            type: 'str'
        },
        {
            name: 'warm_start',
            value: 'false',
            type: 'bool'
        },
        {
            name: 'min_samples_split',
            value: '0',
            type: 'str'
        },
        {
            name: 'min_samples_leaf',
            value: '1',
            type: 'str'
        },
        {
            name: 'min_weight_fraction_leaf',
            value: '0',
            type: 'str'
=======
            type: 'model'
        },{
            name: 'kernel',
            value: 'rbf',
            type:'str'
        }, {
            name: 'C',
            value: '1.0',
            type:'str'
        },
        {
            name: 'coef0',
            value: '0.0',
            type:'str'
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        },
        {
            name: 'n_jobs',
            value: '1',
            type: 'str'
        },
        {
            name: 'random_state',
            value: '0',
            type: 'str'
        },                                                                

    ]
}, 'model-card');

// 随机森林
Flow.registerNode('random-forest', {
    label: '随机森林',
    color_type: '#9254DE',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
<<<<<<< HEAD
        [ 0.5, 0, {
            type: 'input'
        }],
        [ 0.5, 1, {
=======
        [0.5, 0, {
            type: 'input'
        }],
        [0.5, 1, {
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type: 'output'
        }]
    ],

    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'random_forest_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
            name: 'n_estimators',
            value: '10',
            type:'str'
        },{
            name: 'min_samples_split',
            value: '2',
            type:'str'
        },{
            name: 'min_samples_leaf',
            value: '1',
            type:'str'
        },
    ]
}, 'model-card');

Flow.registerNode('random-forest-re', {
    label: '随机森林',
    color_type: '#9254DE',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
<<<<<<< HEAD
        [ 0.5, 0, {
            type: 'input'
        }],
=======
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],

    params: [
        {
            name: 'train',
            value: 'false',
<<<<<<< HEAD
            type:'bool'
=======
            type:'bool'            
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
<<<<<<< HEAD
            value: 'random_forest_re_test',
=======
            value: 'decision_tree_test',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
<<<<<<< HEAD
            name: 'n_estimators',
            value: '10',
            type:'str'
        },{
=======
            name: 'min_samples_split',
            value: '2',
            type:'str'
        },{
            name: 'min_samples_leaf',
            value: '1',
            type:'str'
        },

    ]
}, 'model-card');
Flow.registerNode('DecisionTreeRe', {
    label: '决策树',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'            
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'decision_tree_re_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },
        {
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            name: 'min_samples_split',
            value: '2',
            type:'str'
        },{
            name: 'min_samples_leaf',
            value: '1',
            type:'str'
        },
    ]
}, 'model-card');

<<<<<<< HEAD
// 朴素贝叶斯
Flow.registerNode('Bayes', {
    label: '朴素贝叶斯',
    color_type: '#6495ED',
=======
Flow.registerNode('BiClassificationEval', {
    label: '二分类评估',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }],

    ],
    params: [
        {
            name:'f1',
            value:'true',
            type:'bool'
        },{
            name:'accuracy',
            value:'true',
            type: 'bool'
        },{
            name:'txt_report',
            value:'true',
            type:'bool'
        },{
            name:'curve',
            value:'roc',
            options: ['roc','pr'],
            type:'option'
        }
    ]
}, 'model-card');

Flow.registerNode('ClassificationEval', {
    label: '多分类评估',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }],

    ],
    params: [
        {
            name: 'txt_report',
            value: 'true',
            type: 'bool'
        }
    ]
}, 'model-card');

Flow.registerNode('LogisticRegression', {
    label: '逻辑回归',
    color_type: '#FFFF00',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/uZVdwjJGqDooqKLKtvGA.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
        }],
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]        
    ],
    params: [
        {
            name: 'train',
            value: 'false',
<<<<<<< HEAD
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str',
        }, {
            name: 'model_name',
            value: 'Bayes_test',
=======
            type:'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str'
        },{
            name: 'max_iter',
            value: '10',
            type: 'str'
        }, 
        {
            name: 'model_name',
            value: 'logistic_regression_test',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
            type:'str'
        },
        {
            name: 'model',
            value: '',
<<<<<<< HEAD
            type:'model'
        },{
            name: 'alpha',
            value: '1.0',
            type:'str'
        },{
            name: 'fit_prior',
            value: 'true',
            type:'bool'
        },{
            name: 'norm',
            value: 'false',
            type:'bool'
=======
            type: 'model'
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        },

    ]
}, 'model-card');
<<<<<<< HEAD
Flow.registerNode('BayesianRidge', {
    label: '贝叶斯线性回归',
    color_type: '#6495ED',
=======

Flow.registerNode('Impute', {
    label: '缺失值填充',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'strategy',
            value: 'mean',
            options: ['mean', '0','bfill', 'ffill'],
            type: 'option'
        }, 
    ]
}, 'model-card');

Flow.registerNode('Normalize', {
    label: '特征正则化',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'norm',
            value: 'l2',
            options: ['l2', 'l1', 'max'],
            type: 'option'
        },
    ]
}, 'model-card');

Flow.registerNode('DatasetGenerate', {
    label: '数据集生成',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'label_col',
            value: 'label',
            type: 'str',
            tip:'类别所在列，其余列视为特征列'
        },
    ]
}, 'model-card');

Flow.registerNode('DeleteColumn', {
    label: '列删除',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'col',
            value: '',
            type: 'str',
            tip:'多个列请用,隔开'
        },
    ]
}, 'model-card');


Flow.registerNode('KNearestNeighbor', {
    label: 'K近邻',
    color_type: '#9ACD32',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/uZVdwjJGqDooqKLKtvGA.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 0, {
            type: 'input'
        }],
        [ 0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
<<<<<<< HEAD
            type:'str',
        }, {
            name: 'model_name',
            value: 'BayesianRidge_test',
            type:'str'
=======
            type: 'str',
            tip:'验证集所占比例'
        }, {
            name: 'model_name',
            value: 'KNearestNeighbor_test',
            type: 'str',
            tip:'训练保存的模型名称'
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },{
            name: 'n_neighbors',
            value: '5',
            type: 'str'
        },{
            name: 'leaf_size',
            value: '30',
            type: 'str'
        },{
            name: 'p',
            value: '2',
            type: 'str'
        },

    ]
}, 'model-card');
Flow.registerNode('KNearestNeighborRe', {
    label: 'K近邻',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
            tip:'验证集所占比例'
        }, {
            name: 'model_name',
            value: 'KNearestNeighbor_re_test',
            type: 'str',
            tip:'训练保存的模型名称'
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },{
            name: 'n_neighbors',
            value: '5',
            type: 'str'
        },{
            name: 'leaf_size',
            value: '30',
            type: 'str'
        },{
            name: 'p',
            value: '2',
            type: 'str'
        },

    ]
}, 'model-card');

Flow.registerNode('AdaBoost', {
    label: 'AdaBoost',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'AdaBoost_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },
        {
            name: 'n_estimators',
            value: '50',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '1.0',
            type: 'str',
        },


    ]
}, 'model-card');

Flow.registerNode('AdaBoostRe', {
    label: 'AdaBoost',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'AdaBoost_re_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        }, {
            name: 'n_estimators',
            value: '50',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '1.0',
            type: 'str',
        },

    ]
}, 'model-card');

Flow.registerNode('GradientBoosting', {
    label: '梯度提升树',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'GradientBoosting_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },
        {
            name: 'n_estimators',
            value: '100',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '0.1',
            type: 'str',
        },
    ]
}, 'model-card');
Flow.registerNode('GradientBoostingRe', {
    label: '梯度提升树',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'GradientBoosting_re_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        }, {
            name: 'n_estimators',
            value: '100',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '0.1',
            type: 'str',
        },

    ]
}, 'model-card');

Flow.registerNode('SGDRegressor', {
    label: '随机梯度下降',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'SGDRegressor_test',
            type: 'str',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        },
        {
            name: 'model',
            value: '',
<<<<<<< HEAD
            type:'model'
        },{
            name: 'n_iter',
            value: '300',
            type:'str'
        },{
            name: 'compute_score',
            value: 'false',
            type:'bool'
        },
    ]
}, 'model-card');
// 读数据表
Flow.registerNode('FileComponent', {
    label: '数据格式转换',
    color_type: '#FAAD14',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'inType',
            value: 'csv',
            options: ['csv','json','txt'],
            type:'option'
        }, {
            name: 'outType',
            value: 'json',
            options: ['csv', 'json', 'txt'],
            type:'option'
        },

    ]
}, 'model-card');

Flow.registerNode('Preview', {
    label: '数据文件预览',
    color_type: '#FAAD14',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
    ]
}, 'model-card');

Flow.registerNode('SupportVectorMachine', {
    label: '支持向量机',
    color_type: '#FF0000',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'SVM_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },
        {
            name: 'kernel',
            value: 'rbf',
            type:'str'
        }, {
            name: 'C',
            value: '1.0',
            type:'str'
        },
        {
            name: 'coef0',
            value: '0.0',
            type:'str'
        },
    ]
}, 'model-card');

Flow.registerNode('SupportVectorRegression', {
    label: '支持向量回归',
    color_type: '#CDCDC1',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str'
        }, {
            name: 'model_name',
            value: 'SVR_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },{
            name: 'kernel',
            value: 'rbf',
            type:'str'
        }, {
            name: 'C',
            value: '1.0',
            type:'str'
        },
        {
            name: 'coef0',
            value: '0.0',
            type:'str'
        },

    ]
}, 'model-card');

Flow.registerNode('LinearRegression', {
    label: '线性回归',
    color_type: '#0000EE',
=======
            type: 'model'
        },
        {
            name: 'max_iter',
            value: '100',
            type: 'str',
        },
        {
            name: 'fit_intercept',
            value: 'true',
            type: 'bool',
        },
    ]
}, 'model-card');
Flow.registerNode('SGDClassifier', {
    label: '随机梯度下降',
    color_type: '#9ACD32',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
<<<<<<< HEAD
        [0.5, 0, {
            type: 'input'
        }],
=======
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
<<<<<<< HEAD
            type:'bool',
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'linear_test',
            type:'str'
=======
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
            tip:'验证集所占比例'
        }, {
            name: 'model_name',
            value: 'SGDClassifier_test',
            type: 'str',
            tip:'训练保存的模型名称'
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        },
        {
            name: 'model',
            value: '',
            type: 'model'
<<<<<<< HEAD
        },

    ]
}, 'model-card');

Flow.registerNode('DecisionTree', {
    label: '决策树',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'            
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'decision_tree_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },{
            name: 'min_samples_split',
            value: '2',
            type:'str'
        },{
            name: 'min_samples_leaf',
            value: '1',
            type:'str'
        },

    ]
}, 'model-card');
Flow.registerNode('DecisionTreeRe', {
    label: '决策树',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [ 0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'            
        }, {
            name: 'ratio',
            value: '0.2',
            type:'str'
        }, {
            name: 'model_name',
            value: 'decision_tree_re_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type:'model'
        },
        {
            name: 'min_samples_split',
            value: '2',
            type:'str'
        },{
            name: 'min_samples_leaf',
            value: '1',
            type:'str'
        },

    ]
}, 'model-card');

Flow.registerNode('BiClassificationEval', {
    label: '二分类评估',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }],

    ],
    params: [
        {
            name:'f1',
            value:'true',
            type:'bool'
        },{
            name:'accuracy',
            value:'true',
            type: 'bool'
        },{
            name:'txt_report',
            value:'true',
            type:'bool'
        },{
            name:'curve',
            value:'roc',
            options: ['roc','pr'],
            type:'option'
        }
    ]
}, 'model-card');

Flow.registerNode('ClassificationEval', {
    label: '多分类评估',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }],

    ],
    params: [
        {
            name: 'txt_report',
            value: 'true',
            type: 'bool'
        }
    ]
}, 'model-card');

Flow.registerNode('RegressionEval', {
    label: '回归评估',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }],

    ],
    params: [
        {
            name: 'txt_report',
            value: 'true',
            type: 'bool'
        }
    ]
}, 'model-card');
Flow.registerNode('ClusterEval', {
    label: '聚类评估',
    color_type: '#1C1C1C',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 0, {
            type: 'input'
        }],

    ],
    params: [
        {
            name: 'txt_report',
            value: 'true',
            type: 'bool'
        }
    ]
}, 'model-card');

Flow.registerNode('LogisticRegression', {
    label: '逻辑回归',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]        
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type:'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str'
        },{
            name: 'max_iter',
            value: '10',
            type: 'str'
        }, 
        {
            name: 'model_name',
            value: 'logistic_regression_test',
            type:'str'
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },

    ]
}, 'model-card');

Flow.registerNode('Impute', {
    label: '缺失值填充',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'strategy',
            options: ['mean', '0','bfill', 'ffill'],
            type: 'option_add',
            value: [
                {
                    'column':"",
                    'value':""
                }
            ]
        }, 
    ]
}, 'model-card');

Flow.registerNode('CategoryEncoder', {
    label: '类别编码',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'strategy',
            options: ['onehot','factorize'],
            type: 'option_add',
            value: [
                {
                    'column': "",
                    'value': ""
                }
            ]
        },
    ]
}, 'model-card');


Flow.registerNode('Scaler', {
    label: '值缩放',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'strategy',
            options: ['standard', 'minmax','maxabs'],
            type: 'option_add',
            value: [
                {
                    'column': "",
                    'value': ""
                }
            ]
        },
    ]
}, 'model-card');

Flow.registerNode('KBinsDiscretizer', {
    label: 'K桶离散化',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'nbins',
            options: ['2','3','4','5','6','7','8','9','10'],
            type: 'option_add',
            value: [
                {
                    'column': "",
                    'value': ""
                }
            ]
        },
    ]
}, 'model-card');





Flow.registerNode('Normalize', {
    label: '特征正则化',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'norm',
            value: 'l2',
            options: ['l2', 'l1', 'max'],
            type: 'option'
        },
    ]
}, 'model-card');

Flow.registerNode('DatasetGenerate', {
    label: '数据集生成',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'label_col',
            value: 'label',
            type: 'str',
            tip:'类别所在列，其余列视为特征列'
=======
        },{
            name: 'max_iter',
            value: '100',
            type: 'str',
        },
        {
            name: 'fit_intercept',
            value: 'true',
            type: 'bool',
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
        },
    ]
}, 'model-card');

<<<<<<< HEAD
Flow.registerNode('DeleteColumn', {
    label: '列删除',
    color_type: '#FFFF00',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }],
        [0.5, 0, {
            type: 'input'
        }]
    ],
    params: [
        {
            name: 'col',
            value: '',
            type: 'str',
            tip:'多个列请用,隔开'
        },
    ]
}, 'model-card');


Flow.registerNode('KNearestNeighbor', {
    label: 'K近邻',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
            tip:'验证集所占比例'
        }, {
            name: 'model_name',
            value: 'KNearestNeighbor_test',
            type: 'str',
            tip:'训练保存的模型名称'
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },{
            name: 'n_neighbors',
            value: '5',
            type: 'str'
        },{
            name: 'leaf_size',
            value: '30',
            type: 'str'
        },{
            name: 'p',
            value: '2',
            type: 'str'
        },

    ]
}, 'model-card');
Flow.registerNode('KNearestNeighborRe', {
    label: 'K近邻',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
            tip:'验证集所占比例'
        }, {
            name: 'model_name',
            value: 'KNearestNeighbor_re_test',
            type: 'str',
            tip:'训练保存的模型名称'
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },{
            name: 'n_neighbors',
            value: '5',
            type: 'str'
        },{
            name: 'leaf_size',
            value: '30',
            type: 'str'
        },{
            name: 'p',
            value: '2',
            type: 'str'
        },

    ]
}, 'model-card');

Flow.registerNode('AdaBoost', {
    label: 'AdaBoost',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'AdaBoost_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },
        {
            name: 'n_estimators',
            value: '50',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '1.0',
            type: 'str',
        },


    ]
}, 'model-card');

Flow.registerNode('AdaBoostRe', {
    label: 'AdaBoost',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'AdaBoost_re_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        }, {
            name: 'n_estimators',
            value: '50',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '1.0',
            type: 'str',
        },

    ]
}, 'model-card');

Flow.registerNode('GradientBoosting', {
    label: '梯度提升树',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'GradientBoosting_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },
        {
            name: 'n_estimators',
            value: '100',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '0.1',
            type: 'str',
        },
    ]
}, 'model-card');
Flow.registerNode('GradientBoostingRe', {
    label: '梯度提升树',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'GradientBoosting_re_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        }, {
            name: 'n_estimators',
            value: '100',
            type: 'str',
        },{
            name: 'learning_rate',
            value: '0.1',
            type: 'str',
        },

    ]
}, 'model-card');

Flow.registerNode('SGDRegressor', {
    label: '随机梯度下降',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
        }, {
            name: 'model_name',
            value: 'SGDRegressor_test',
            type: 'str',
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },
        {
            name: 'max_iter',
            value: '100',
            type: 'str',
        },
        {
            name: 'fit_intercept',
            value: 'true',
            type: 'bool',
        },
    ]
}, 'model-card');
Flow.registerNode('SGDClassifier', {
    label: '随机梯度下降',
    color_type: '#9ACD32',
    type_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/czNEJAmyDpclFaSucYWB.svg',
    state_icon_url: 'https://gw.alipayobjects.com/zos/rmsportal/MXXetJAxlqrbisIuZxDO.svg',
    // 设置锚点
    anchor: [
        [0.5, 1, {
            type: 'output'
        }]
    ],
    params: [
        {
            name: 'train',
            value: 'false',
            type: 'bool'
        }, {
            name: 'ratio',
            value: '0.2',
            type: 'str',
            tip:'验证集所占比例'
        }, {
            name: 'model_name',
            value: 'SGDClassifier_test',
            type: 'str',
            tip:'训练保存的模型名称'
        },
        {
            name: 'model',
            value: '',
            type: 'model'
        },{
            name: 'max_iter',
            value: '100',
            type: 'str',
        },
        {
            name: 'fit_intercept',
            value: 'true',
            type: 'bool',
        },

    ]
}, 'model-card');

=======
>>>>>>> 26834db2e373429b3393ac8503d74372ba3ef35f
Flow.registerEdge('line', {
    draw(item) {
        const group = item.getGraphicGroup();
        const path = this.getPath(item);
        let aaa = group.addShape('path', {
            attrs: {
                path,
                stroke: 'black',
                // lineWidth: width,
                endArrow: true,
                lineDash: [10, 10]
            }
        });

        return aaa;
    },
    getPath(item) {
        const points = item.getPoints();
        return G6Editor.Util.pointsToPolygon(points);
    },
    afterDraw: function afterDraw(item) {
        var keyShape = item.getKeyShape();
        keyShape.attr('lineDashOffset', 0);
        keyShape.animate({
            lineDashOffset: -20,
            repeat: true
        }, 500);
    }
});
