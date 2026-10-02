export interface ApiResponse<T> {
  success: boolean;
  msg: string;
  data: T;
  timestamp?: string;
}

export interface AuthResponse {
  token: string;
  tokenType: string;
  id: number;
  email: string;
  fullName: string;
  roles: string[];
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
}

export interface UserProfile {
  id: number;
  email: string;
  authorities: { authorities: string }[];
}
