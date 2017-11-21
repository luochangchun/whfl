package org.marker.mushroom.beans;


/**
 * 登录消息
 * 
 * @author marker
 */
public class ResultMessage
{

	//状态
	private boolean status;
	//消息
	private String message;
	// 传值
	private String param;
	
	
	/**
	 * 返回信息
	 */
	public ResultMessage(){
		
	};
	
	/**
	 * 返回信息
	 */
	public ResultMessage(final String message){
		this.message=message;
	}
	
	/**
	 * 登录消息
	 *
	 * @param boolean
	 *           status
	 * @param String
	 *           message
	 */
	public ResultMessage(final boolean status, final String message)
	{
		this.status = status;
		this.message = message;
	}

	/**
	 * 返回消息
	 * 
	 * @param status
	 * @param message
	 * @param param
	 */
	public ResultMessage(final boolean status, final String message, final String param)
	{
		this.status = status;
		this.message = message;
		this.param = param;
	}
	
	/**
	 * 校验前台验证码
	 *
	 * @param boolean
	 *           status
	 */
	public ResultMessage(final boolean status)
	{
		this.status = status;
	}

	public boolean isStatus()
	{
		return status;
	}

	public void setStatus(final boolean status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(final String message)
	{
		this.message = message;
	}

	public String getParam()
	{
		return param;
	}

	public void setParam(final String param)
	{
		this.param = param;
	}
}
