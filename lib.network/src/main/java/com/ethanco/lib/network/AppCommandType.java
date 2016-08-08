package com.ethanco.lib.network;

/**
 * @Description 网络CMD命令
 * Created by EthanCo on 2016/6/17.
 */
public class AppCommandType {

    public static final String SUCCESS = "Success";

    public static final String VERIFY_USER = "VerifyUser";
    public static final String GET_SERVICE_TIME = "GetServerTime";


    /////////////// 场景
    /**
     * 增加/更新 某个场景 的设备
     */
    public static final String APPCOMMAND_TYPE_UPDATE_SENCE_DEVICE = "UpdateUserSenceDevice";

    /**
     * 获得某个场景的详细信息
     */
    public static final String APPCOMMAND_TYPE_GETSCENE = "GetSence";

    /**
     * 新建/编辑场景
     */
    public static final String APPCOMMAND_TYPE_UPDATE_USER_SCENE = "UpdateUserSence";

    /**
     * 删除场景
     */
    public static final String APPCOMMAND_TYPE_DELETE_SCENE = "DeleteSence";

    //////////////场景-某一设备
    /**
     * 获取 场景-某一设备 - 命令 的 group
     */
    public static String AppCommand_TYPE_GET_DEVICE_COMMAND = "GetDeviceCommandAction";

    /**
     * 获取 场景-某一设备 - 命令 的 child
     */
    public static final String APPCOMMAND_TYPE_GET_DEVICE_COMMAND_PARAMS = "GetDeviceCommandActionParams";

    /**
     * 保存 场景-某一设备 - 命令(动作)
     */
    public static final String APPCOMMAND_TYPE_UPDATE_DEVICE_ACTION = "UpdateUserSenceDeviceAction";

    ///////////////简易密码
    /**
     * 保存简易密码
     */
    public static final String APPCOMMAND_TYPE_SET_SIMPLE_PWD = "UpdateUserSimplePWD";
    /**
     * 验证简易密码
     */
    public static final String APPCOMMAND_TYPE_VERIFY_SIMPLE_PWD = "verifySimplePwd";
    /**
     * 获取短信验证码（重置、找回简易密码）
     */
    public static final String APPCOMMAND_TYPE_RESET_SIMPLE_PWD = "GetResetSimplePWDLicenseCode";

    /***
     * 心跳
     */
    public static String APPCOMMAND_TYPE_HEARTTICK = "HeartTick";
    /***
     * 得到服务器时间
     */
    public static String APPCOMMAND_TYPE_GETSERVERTIME = "GetServerTime";
    // /////////////用户模块
    /***
     * 注册
     ***/
    public static String APPCOMMAND_TYPE_REGISTER = "Register";
    /***
     * 验证码
     ***/
    public static String APPCOMMAND_TYPE_VERIFYCODE = "VerifyCode";
    /***
     * 用户登录验证
     ***/
    public static String APPCOMMAND_TYPE_VERIFYUSER = "VerifyUser";
    /**    */
    public static String APPCOMMAND_TYPE_VERIFYEXTERNALUSER = "VerifyExternalUser";
    /***
     * 修改密码
     ***/
    public static String APPCOMMAND_TYPE_CHANGEPASSWORD = "ChangePassword";
    /***
     * 重置密码
     ***/
    public static String APPCOMMAND_TYPE_RESETPASSWORD = "ResetPassword";
    /***
     * 修改用户名称
     ***/
    public static String APPCOMMAND_TYPE_EDITHOMENAME = "EditHomeName";
    // /////////////设备模块
    /***
     * 添加设备
     ***/
    public static String APPCOMMAND_TYPE_ADDDEVICE = "AddDevice";
    /***
     * 删除设备
     ***/
    public static String APPCOMMAND_TYPE_DELETEDEVICE = "DeleteDevice";

    /***
     * 需要设备名字
     ***/
    public static String APPCOMMAND_TYPE_EDITDEVICE = "EditDevice";
    /***
     * 分享设备
     ***/
    public static String APPCOMMAND_TYPE_SHAREDEVICE = "ShareDevice";
    /***
     * 撤销分享
     ***/
    public static String APPCOMMAND_TYPE_CANCELSHARE = "CancelShare";
    /***
     * 分享设备通知
     ***/

    public static String APPCOMMAND_TYPE_SHAREDEVICENOTICE = "ShareDeviceNotice";
    /***
     * 分享设备用户列表
     ***/
    public static String APPCOMMAND_TYPE_SHAREDEVICEUSERS = "ShareDeviceUsers";
    /***
     * 接受分享
     ***/
    public static String APPCOMMAND_TYPE_ACCEPTSHARE = "AcceptShare";
    /***
     * 设备登录验证
     ***/
    public static String APPCOMMAND_TYPE_VERIFYDEVICE = "VerifyDevice";
    // ///////////场景模块
    /***
     * 添加场景
     ***/
    public static String APPCOMMAND_TYPE_ADDSENCE = "AddSence";
    /***
     * 删除场景
     ***/
    public static String APPCOMMAND_TYPE_DELECTSENCE = "DelectSence";
    /***
     * 添加动作
     ***/
    public static String APPCOMMAND_TYPE_ADDACTION = "AddAction";
    /***
     * 删除动作
     ***/
    public static String APPCOMMAND_TYPE_DELETEACTION = "DeleteAction";
    /***
     * 添加场景设备
     ***/
    public static String APPCOMMAND_TYPE_ADDSENCEDEVICE = "AddSenceDevice";
    /***
     * 删除场景设备
     ***/
    public static String APPCOMMAND_TYPE_DELECTESENCEDEVICE = "DelecteSenceDevice";
    /***
     * 添加房间
     ***/
    public static String APPCOMMAND_TYPE_ADDROOM = "AddRoom";
    /***
     * 删除房间
     ***/
    public static String APPCOMMAND_TYPE_DELECTEROOM = "DelecteRoom";
    /***
     * 指定设备所在房间
     ***/
    public static String APPCOMMAND_TYPE_DEVICEROOMSET = "DeviceRoomSet";
    /***
     * 获取设备
     ***/
    public static String APPCOMMAND_TYPE_GETDEVICES = "GetDevices";
    /***
     * 获取场景
     ***/
    public static String APPCOMMAND_TYPE_GETSENCES = "GetSences";
    /***
     * 获取房间
     ***/
    public static String APPCOMMAND_TYPE_GETROOMS = "GetRooms";
    /***
     * 获得设备的状态
     ***/
    public static String APPCOMMAND_TYPE_INITSTATE = "InitState";

    /**
     * 取得设备列表
     * */
    public static String APPCOMMAND_TYPE_GETDEVICESBYPARENTID = "GetDevicesByParentId";

    /**
    * 取得歌曲列表
    **/
    public static String APPCOMMAND_TYPE_GETDEVICESONGINFOS = "GetDeviceSongInfos";
     /**
     * 搜索歌曲列表
     **/
     public static String APPCOMMAND_TYPE_GETDEVICESONGINFOSBYPY = "GetDeviceSongInfosByPY";
    /**
     * 取得专辑列表
     **/
    public static String APPCOMMAND_TYPE_GETALBUMNAMES = "GetAlbumNames";
    /**
     * 搜索专辑列表
     **/
    public static String APPCOMMAND_TYPE_GETALBUMNAMESBYPY = "GetAlbumNamesByPY";
    /**
     * 取得艺人列表
     **/
    public static String APPCOMMAND_TYPE_GETARTISTNAMES = "GetArtistNames";
    /**
     * 搜索艺人列表
     **/
    public static String APPCOMMAND_TYPE_GETARTISTNAMESBYPY = "GetArtistNamesByPY";
}
