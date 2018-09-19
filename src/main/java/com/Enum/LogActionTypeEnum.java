/**
 * Project Name: publish-cmam-content-common
 * File Name: LogActionTypeEnum.java
* Package Name: com.migu.cmam.publish.common.constant.enumcode
 * Date: 2018年2月9日下午3:03:57
 * All rights Reserved, Designed By MiGu. Copyright: Copyright(C) 2018-2020.
 * Company MiGu Co., Ltd.
 *
 */

package com.Enum;

/**
 * Name: LogActionTypeEnum <br/>
 * Description: TODO ADD Description. <br/>
 * Date: 2018年6月14日 下午3:29:00 <br/>
 *
 * @author breeze
 * @version
 * @see
 */
public enum LogActionTypeEnum {

    /**
     * Start: 业务流程开始.
     */
    Start("1", "action_start"),
    /**
     * Complete: 业务流程完成.
     */
    Complete("2", "action_complete"),
    /**
     * Common: 通用方法.
     */
    Common("3", "action_common"),
    /**
     * Verify: 校验.
     */
    Verify("4", "action_verify"),
    /**
     * Create: 数据库创建.
     */
    Create("5", "action_create"),
    /**
     * Update: 数据库更新.
     */
    Update("6", "action_update"),
    /**
     * Delete: 数据库删除.
     */
    Delete("7", "action_delete"),
    /**
     * Find: 数据库查找.
     */
    Find("8", "action_find"),
    /**
     * Index: 索引.
     */
    Index("9", "action_index"),
    /**
     * Download: 下载业务相关.
     */
    Download("10", "action_download"),
    /**
     * Response:返回值
     */
    Response("11", "action_response"),
    /**
     * Enter: 进入方法.
     */
    Enter("101", "action_enter"),
    /**
     * Leave: 离开方法.
     */
    Leave("102", "action_leave");
    private String code;
    private String msg;

    LogActionTypeEnum(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * code.
     *
     * @return the code
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * code.
     *
     * @param code
     *            the code to set
     * 
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * msg.
     *
     * @return the msg
     * 
     */
    public String getMsg() {
        return msg;
    }

    /**
     * msg.
     *
     * @param msg
     *            the msg to set
     * 
     */
    public void setMsg(final String msg) {
        this.msg = msg;
    }
}
