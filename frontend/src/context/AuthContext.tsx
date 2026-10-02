import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import api from "../api/axios";

import {
  AuthResponse,
  ApiResponse,
  LoginRequest,
  RegisterRequest,
} from "../types/auth";

interface AuthContextType {
  user: AuthResponse | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (credential: LoginRequest) => Promise<void>;
  register: (credential: RegisterRequest) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [user, setUser] = useState<AuthResponse | null>(null);

  const [token, setToken] = useState<string | null>(
    localStorage.getItem("token"),
  );

  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    const savedData = localStorage.getItem("user");
    const savedToken = localStorage.getItem("token");
    if (savedData && savedToken) {
      setToken(savedToken);
      try {
        setUser(JSON.parse(savedData));
      } catch (e) {
        console.log("Failed to parse Stored User ", e);
      }
    }
    setIsLoading(false);
  }, [token]);

  const login = async (credential: LoginRequest) => {
    const response = await api.post<ApiResponse<AuthResponse>>(
      "/auth/login",
      credential,
    );

    const authData = response.data.data;
    localStorage.setItem("token", authData.token);
    localStorage.setItem("user", JSON.stringify(authData));
    setToken(authData.token);
    setUser(authData);
  };

  const register = async (credential: RegisterRequest) => {
    const response = await api.post<ApiResponse<AuthResponse>>(
      "/auth/register",
      credential,
    );
    const authData = response.data.data;
    localStorage.setItem("token", authData.token);
    localStorage.setItem("user", JSON.stringify(authData));
    setToken(authData.token);
    setUser(authData);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        isAuthenticated: !!token,
        isLoading,
        login,
        register,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error(" useAuth must be used within a AuthProvider ");
  }
  return context;
};
