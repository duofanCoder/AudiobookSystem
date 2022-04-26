import { Category } from '../enum/business';

export namespace Condition {
  export interface Common {
    name: string;
    pageSize: number;
    pageNum: number;
  }
  export interface User {
    pageSize: number;
    pageNum: number;
    name: string;
    gander: boolean;
  }

  export interface Book {
    pageSize: number;
    pageNum: number;
    name: string;
    author: string;
    category: Category;
  }

  export interface Chapter {
    pageSize: number;
    pageNum: number;
    bookId: number;
  }
}
