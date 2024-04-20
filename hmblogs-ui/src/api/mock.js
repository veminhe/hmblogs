import Mock from 'mockjs'

import permission from './mockServe/permission'

// 登录权限
Mock.mock(/api\/permission\/getMenu/,'post',permission.getMenu)