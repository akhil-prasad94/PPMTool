package com.neu.kanbanproject.ppmtool.payload;





public class JWTSuccessResponse {


    private boolean success;
    private String token;

    public JWTSuccessResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "JWTSuccessResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }

    public void setToken(String token) {
        this.token = token;
    }
}
