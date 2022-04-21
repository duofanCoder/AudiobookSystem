import { useRouterPush } from '@/router/router';
import { fetchLogin, fetchUserInfo } from '@/service';
import { clearAuthStorage, getUserInfo, setToken, setUserInfo } from '@/utils';
import { defineStore } from 'pinia';
import { useState } from '../Storage';

const state = useState();
export interface IUserState {
  token: string;
  userInfo: Dto.UserInfo;
}

export const useUserStore = defineStore({
  id: 'app-user',
  state: (): IUserState => ({
    token: state.value.ACCESS_TOKEN,
    userInfo: getUserInfo(),
  }),
  getters: {
    isLogin(state) {
      return Boolean(state.token);
    },
    userInfo(): IUserState {
      return this.userInfo;
    },
  },
  actions: {
    setToken(token: string) {
      this.token = token;
    },
    setUserInfo(info: Dto.UserInfo) {
      setUserInfo(info);
    },
    async loginByToken(backendToken: Dto.Token) {
      const { toLoginRedirect } = useRouterPush(false);

      // 先把token存储到缓存中
      const { token } = backendToken;
      setToken(token);

      // 获取用户信息
      const { data } = await fetchUserInfo();
      if (data) {
        // 成功后把用户信息存储到缓存中
        setUserInfo(data);
        // 更新状态
        Object.assign(this, { userInfo: data, token });
        // 跳转登录后的地址
        toLoginRedirect();

        // 登录成功弹出欢迎提示
        window.$notification?.success({
          title: '登录成功!',
          content: `欢迎回来，${data.nickname}!`,
          duration: 3000,
        });
      } else {
        // 不成功则重置状态
        this.resetAuthStore();
      }
    },
    /**
     * 登录
     * @param phone - 手机号
     * @param pwdOrCode - 密码或验证码
     * @param type - 登录方式: pwd - 密码登录; sms - 验证码登录
     */
    async login(email: string, password: string) {
      // 同步等待，直接出data
      const { data } = await fetchLogin(email, password);
      if (data) {
        await this.loginByToken(data);
      }
    },

    logout() {
      this.resetAuthStore();
    },
    /** 重置auth状态 */
    resetAuthStore() {
      const { toHome } = useRouterPush(false);
      clearAuthStorage();
      this.$reset();
      toHome();
    },
  },
});
