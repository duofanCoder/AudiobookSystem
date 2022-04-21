declare namespace Dto {
  interface Token {
    prefix: string;
    expiration: number;
    token: string;
  }
  interface UserInfo {
    id: number;
    username: string;
    nickname: string;
    mobile: string;
    role: string;
    avatar: string;
    birth: string;
    gender: boolean;
    shelf: Array<Book>;
  }

  interface Book {
    title: string;
    author: string;
    img: string;
    description: string;
  }
}
