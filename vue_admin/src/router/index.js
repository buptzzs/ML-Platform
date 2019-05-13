import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },
  {
    path: '/resourceManage',
    component: Layout,
    meta: { title: '文件管理', icon: 'manage' },
    redirect: '/resourceManage/data',
    children: [
      {
        path: '/resourceManage/data',
        name: 'data',
        component: () => import('@/views/resourceManage/index'),
        props: { type: 'data'},
        meta: { title: '数据文件', icon: 'file'}

      },
      {
        path: '/resourceManage/model',
        name:'model',
        component: () => import('@/views/resourceManage/index'),
        props: { type: 'model' },
        meta: { title: '模型文件', icon: 'file' }

      }
    ]
    
  },{
    path: '/taskManage',
    component: Layout,
    meta: { title: '任务台', icon: 'manage' },    
    children: [
      {
        path: '/taskManage/index',
        name: 'TaskManage',
        component: () => import('@/views/taskManage/index'),
        meta: { title: '任务管理' , icon: 'tasks'},
      },{
        path: '/editor',
        name: 'editor',
        component: () => import('@/views/editor/model-flow'),
        meta: { title:'算法建模', icon: 'tree'}
      }

    ]
  },{
    path: '/monitor',
    component: Layout,
    redirect: '/monitor/index',    
    name:'system',
    meta: { title: '系统资源监控', icon: '' },    
    children: [{
      path: 'index',
      component: () => import('@/views/monitor/index'),
      meta: { title: '系统资源监控', icon: 'monitor' },    
    }]
  },


  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
