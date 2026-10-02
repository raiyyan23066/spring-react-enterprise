import React, { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import api from "../api/axios";
import { UserProfile, ApiResponse } from "../types/auth";
import { LogOut, User, Mail, ShieldCheck, LayoutDashboard } from "lucide-react";

export const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await api.get<ApiResponse<UserProfile>>("/users/me");
        setProfile(response.data.data);
      } catch (err) {
        console.error("Failed to fetch user profile:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, []);

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Top Navbar */}
      <nav className="bg-white border-b border-gray-200">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16 items-center">
            <div className="flex items-center gap-2">
              <div className="h-9 w-9 rounded-lg bg-indigo-600 flex items-center justify-center text-white font-bold">
                <LayoutDashboard className="h-5 w-5" />
              </div>
              <span className="text-xl font-bold text-gray-900">
                Enterprise Portal
              </span>
            </div>

            <div className="flex items-center gap-4">
              <span className="text-sm font-medium text-gray-700">
                {user?.fullName}
              </span>
              <button
                onClick={logout}
                className="inline-flex items-center gap-2 px-3 py-1.5 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 transition cursor-pointer"
              >
                <LogOut className="h-4 w-4 text-gray-500" />
                Logout
              </button>
            </div>
          </div>
        </div>
      </nav>

      {/* Main Content Area */}
      <main className="max-w-7xl mx-auto py-10 px-4 sm:px-6 lg:px-8">
        <div className="bg-white shadow rounded-lg p-6 border border-gray-100">
          <h2 className="text-2xl font-bold text-gray-900 mb-6">
            User Profile Overview
          </h2>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="p-4 bg-gray-50 rounded-lg border border-gray-200 flex items-center gap-4">
              <div className="p-3 bg-indigo-100 rounded-full text-indigo-600">
                <User className="h-6 w-6" />
              </div>
              <div>
                <p className="text-xs font-semibold uppercase tracking-wider text-gray-500">
                  Full Name
                </p>
                <p className="text-lg font-medium text-gray-900">
                  {user?.fullName || "N/A"}
                </p>
              </div>
            </div>

            <div className="p-4 bg-gray-50 rounded-lg border border-gray-200 flex items-center gap-4">
              <div className="p-3 bg-indigo-100 rounded-full text-indigo-600">
                <Mail className="h-6 w-6" />
              </div>
              <div>
                <p className="text-xs font-semibold uppercase tracking-wider text-gray-500">
                  Email Address
                </p>
                <p className="text-lg font-medium text-gray-900">
                  {user?.email || "N/A"}
                </p>
              </div>
            </div>

            <div className="p-4 bg-gray-50 rounded-lg border border-gray-200 flex items-center gap-4">
              <div className="p-3 bg-indigo-100 rounded-full text-indigo-600">
                <ShieldCheck className="h-6 w-6" />
              </div>
              <div>
                <p className="text-xs font-semibold uppercase tracking-wider text-gray-500">
                  Assigned Roles
                </p>
                <p className="text-lg font-medium text-gray-900">
                  {user?.roles?.join(", ") || "ROLE_USER"}
                </p>
              </div>
            </div>
          </div>

          {loading ? (
            <div className="mt-8 text-sm text-gray-500">
              Loading details from Spring Boot...
            </div>
          ) : (
            profile && (
              <div className="mt-8 pt-6 border-t border-gray-200">
                <h3 className="text-sm font-semibold uppercase tracking-wider text-gray-500 mb-2">
                  Verified Backend Profile Data
                </h3>
                <div className="p-4 bg-gray-900 text-green-400 rounded-lg font-mono text-sm overflow-x-auto">
                  {JSON.stringify(profile, null, 2)}
                </div>
              </div>
            )
          )}
        </div>
      </main>
    </div>
  );
};
