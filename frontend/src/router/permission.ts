import { useState } from '@/store/Storage';
import { NavigationGuardNext, RouteLocationNormalized, Router } from 'vue-router';
import { useUserStore } from '@/store';
import { getToken } from '@/utils';

export async function createPermissionGuard(
  to: RouteLocationNormalized,
  _from: RouteLocationNormalized,
  next: NavigationGuardNext,
  _router: Router
) {
  const needLogin = Boolean(to.meta?.auth);

  const isLogin = Boolean(getToken());
  const actions: [boolean, () => void][] = [
    // 已登录状态跳转登录页，跳转至首页
    [
      isLogin && to.name === 'login',
      () => {
        next({ name: 'index' });
      },
    ],
    // 不需要登录权限的页面直接通行
    [
      !needLogin,
      () => {
        next();
      },
    ],
    // 未登录状态进入需要登录权限的页面
    [
      !isLogin && needLogin,
      () => {
        const redirect = to.fullPath;
        next({ name: 'login', query: { redirect } });
      },
    ],
    // 登录状态进入需要登录权限的页面，有权限直接通行
    [
      isLogin && needLogin,
      () => {
        next();
      },
    ],
    // [
    //   // 登录状态进入需要登录权限的页面，无权限，重定向到无权限页面
    //   isLogin && needLogin && !hasPermission,
    //   () => {
    //     next({ name: routeName('no-permission') });
    //   },
    // ],
  ];

  actions.some((item) => {
    const [flag, action] = item;
    if (flag) {
      action();
    }
    return flag;
  });
}
