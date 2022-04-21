import { createRequest } from './request';

const httpUrl = import.meta.env.VITE_AXIOS_URL as string;

export const request = createRequest({ baseURL: httpUrl });

export const mockRequest = createRequest({ baseURL: '/mock' });
