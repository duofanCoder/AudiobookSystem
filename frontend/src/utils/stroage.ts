export const getToken = () => {
  let token = window.localStorage.getItem('token');
  if (token == 'undefined') {
    token = null;
  }
  return token;
};

export const saveToken = (token: string) => {
  window.localStorage.setItem('token', token);
};

export const destroyToken = () => {
  window.localStorage.removeItem('token');
};

export default { getToken, saveToken, destroyToken };
