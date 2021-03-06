package com.lemy.telpoo2lib.net;



public class Dataget {
    public static final int CODE_ERROR_SERVER=-2;
    public static final int CODE_ERROR_NETWORK=-1;
    public static final int CODE_SUCCESS=1;
    public static final int CODE_FALSE=2;
    int code=CODE_SUCCESS;
    String msg="error(101)";
    Object data;
    public void setData(Object data){
        this.data=data;
    }

    public void setDataSuccess(Object data){
        this.data=data;
        code=CODE_SUCCESS;
    }

    public String getMsg(){
        if(code==CODE_ERROR_NETWORK)msg="Không có kết nối internet, vui lòng kiểm tra lại!";
        if(code==CODE_ERROR_SERVER)msg="Không thể kết nối đến máy chủ, vui lòng thử lại!";

        return msg;
    }
    public void setMessage(String msg){

        this.msg = msg;
    }

    public int getcode(){
        return code;
    }

    public void setCode(int code){
        this.code=code;
    }

    public void setCode(String strcode){
        try {
            code= Integer.parseInt(strcode);
        }
        catch (NumberFormatException e){
            //Log.e("")
        }
    }

    public void setConnectError(){
        String res = NetSupport.getInstance().methodHttp("url","https://www.google.com",null);
        if(res==null) code=CODE_ERROR_NETWORK;
        else code=CODE_ERROR_SERVER;
    }

    public void setCodeErrorServer(){
       code= Dataget.CODE_ERROR_SERVER;
    }

    public void setSuccess(){
        code=CODE_SUCCESS;
    }

    public void setSuccess(Object data){
        code=CODE_SUCCESS;
        this.data=data;
    }

    public Object getData(){
        return data;
    }
    public String getDataAsString(){
        return data.toString();
    }

    public boolean isSuccess(){
        if(code==CODE_SUCCESS) return true;
        return false;
    }

    public boolean isFailed(){
        if(code==CODE_SUCCESS) return false;
        return true;
    }
}
