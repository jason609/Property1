package com.beanu.arad.error;

import android.content.res.Resources;
import android.text.TextUtils;

import com.beanu.arad.Arad;
import com.beanu.arad.R;

/**
 * API接口，异常错误处理
 * 
 * <pre>
 * 需要在string.xml中添加错误描述,以 code+错误代码 的形式出现
 * </pre>
 */
public class AradException extends Exception {

	private static final long serialVersionUID = -6055929793464170833L;
	private String error;
	private String oriError;
	private String error_code;

	public AradException() {

	}

	public AradException(String detailMessage) {
		error = detailMessage;
	}

	public AradException(String detailMessage, Throwable throwable) {
		error = detailMessage;
	}

	private String getError() {

		String result;

		if (!TextUtils.isEmpty(error)) {
			result = error;
		} else {

			String name = "code" + error_code;
			int i = Arad.app.getResources().getIdentifier(name, "string", Arad.app.getPackageName());

			try {
				result = Arad.app.getString(i);

			} catch (Resources.NotFoundException e) {

				if (!TextUtils.isEmpty(oriError)) {
					result = oriError;
				}

/***************业主端显示的异常code**********************************************
 				else if(error_code.equals("001")){
                    result = "操作失败";
				}
                else if(error_code.equals("002")){
                    result = "无数据";
                }
                else if(error_code.equals("003")){
                    result = "请求参数不正确";
                }
                else if(error_code.equals("004")){
                    result = "未获取到业主的住址信息，请绑定再操作！";
                }
                else if(error_code.equals("005")){
                    result = "未获取到社区到访相关信息";
                }
                else if(error_code.equals("006")){
                    result = "修改社区社区到访相关信息失败";
                }
                else if(error_code.equals("007")){
                    result = "未获取到匹配的业主信息";
                }
                else if(error_code.equals("008")){
                    result = "已经绑定过该业主";
                }
				else if(error_code.equals("009")){
                    result = "未获取到业主验证信息";
                }
				else if(error_code.equals("010")){
					result = "未获取到帖子相关信息";
				}
				else if(error_code.equals("011")){
					result = "未获取到会员注册信息";
				}
				else if(error_code.equals("012")){
					result = "收货地址不存在";
				}
				else if(error_code.equals("013")){
					result = "该地址已经是默认地址";
				}
				else if(error_code.equals("014")){
					result = "默认收货地址不能删除";
				}
				else if(error_code.equals("015")){
					result = "注册的用户已存在";
				}
				else if(error_code.equals("016")){
					result = "请使用邮箱或者手机号码进行注册";
				}
				else if(error_code.equals("017")){
					result = "未获取到商品相关信息 ";
				}
				else if(error_code.equals("018")){
					result = "登录失败，账号或者密码错误";
				}
				else if(error_code.equals("019")){
					result = "原登录密码不正确";
				}
				else if(error_code.equals("020")){
					result = "该用户尚未注册";
				}
				else if(error_code.equals("021")){
					result = "未获取到当前位置附近的社区";
				}
				else if(error_code.equals("022")){
					result = "未获取到订单信息";
				}
				else if(error_code.equals("023")){
					result = "商品库存不足";
				}
				else if(error_code.equals("024")){
					result = "商品已经下架";
				}
				else if(error_code.equals("025")){
					result = "该订单已经评价过";
				}
				else if(error_code.equals("026")){
					result = "未获取到购物车相关信息";
				}
				else if(error_code.equals("027")){
					result = "新增订单详情信息失败";
				}
				else if(error_code.equals("028")){
					result = "新增订单信息失败";
				}
				else if(error_code.equals("029")){
					result = "订单已经被取消";
				}
				else if(error_code.equals("030")){
					result = "订单已经支付 ";
				}
				else if(error_code.equals("031")){
					result = "商家已经接单 ";
				}
				else if(error_code.equals("032")){
					result = "商家已经取消订单";
				}
				else if(error_code.equals("033")){
					result = "已经参与过该调查 ";
				}
				else if(error_code.equals("034")){
					result = "未获取到订单详情信息 ";
				}
				else if(error_code.equals("035")){
					result = "未获取到商品信息（商品不存在）";
				}
				else if(error_code.equals("036")){
					result = "用户不存在或者已被禁用";
				}
				else if(error_code.equals("037")){
					result = "提供的旧手机号码和注册时不匹配";
				}
				else if(error_code.equals("038")){
					result = "通知公告已经签收过";
				}
				else if(error_code.equals("039")){
					result = "所选商品已不在购物车中";
				}
				else if(error_code.equals("040")){
					result = "已经参加过该活动";
				}
				else if(error_code.equals("041")){
					result = "业主认证信息不存在";
				}
				else if(error_code.equals("042")){
					result = "暂不能解除默认业主认证，请先解除其他业主认证";
				}
				else if(error_code.equals("043")){
					result = "该房屋已经是默认房屋";
				}
				else if(error_code.equals("044")){
					result = "缺少物业缴费账单id串";
				}
				else if(error_code.equals("045")){
					result = "未获取到物业账单相关信息，请刷新重试";
				}
				else if(error_code.equals("046")){
					result = "存在已经缴费的物业账单，不可重复缴费";
				}
				else if(error_code.equals("047")){
					result = "更新账单支付信息失败，请刷新重试";
				}
				else if(error_code.equals("048")){
					result = "新增账单记录信息失败，请刷新重试";
				}
				else if(error_code.equals("049")){
					result = "当前时间不在缴费时间段内";
				}
****************业主端显示的异常code****************************************************************************/

/***************员工端显示的异常code*********************************/
				else if(error_code.equals("001")){
					result = "操作失败";
				}
				else if(error_code.equals("002")){
					result = "无数据";
				}
				else if(error_code.equals("003")){
					result = "请求参数不正确";
				}
				else if(error_code.equals("004")){
					result = "更新上下班状态时异常";
				}
				else if(error_code.equals("005")){
					result = "用户名名或者密码错误";
				}
				else if(error_code.equals("006")){
					result = "未获取到员工信息";
				}
				else if(error_code.equals("007")){
					result = "原登录密码不正确";
				}
				else if(error_code.equals("008")){
					result = "未获取到帖子相关信息";
				}
				else if(error_code.equals("009")){
					result = "通知公告已经签收过";
				}
				else if(error_code.equals("010")){
					result = "查询业主住址异常";
				}
				else if(error_code.equals("011")){
					result = "更新业主报修信息时异常";
				}
				else if(error_code.equals("012")){
					result = "新增报修接单记录时异常";
				}
				else if(error_code.equals("013")){
					result = "未获取到报修相关信息";
				}
				else if(error_code.equals("014")){
					result = "当前报修单不符合接单或者拒单要求";
				}
				else if(error_code.equals("015")){
					result = "当前报修单已经被接单，不可重复接单，请刷新";
				}
				else if(error_code.equals("016")){
					result = "未获取到巡更巡查相关信息";
				}
				else if(error_code.equals("017")){
					result = "未获取到巡更点相关信息";
				}
				else if(error_code.equals("018")){
					result = "当前时间不在巡查时间内，不可继续巡更";
				}
				else if(error_code.equals("019")){
					result = "当前巡查点已经巡查过，不可重复巡查";
				}
				else if(error_code.equals("020")){
					result = "已经巡查结束和未完成的不可继续巡查";
				}
				else if(error_code.equals("021")){
					result = "所有的巡查点已经巡查完毕，不可继续巡查";
				}
/****************员工端显示的异常code****************************************************************************/

                else {
                    result = Arad.app.getString(R.string.unknown_error_code) + error_code;
                }
			}
		}

		return result;
	}

	@Override
	public String getMessage() {
		return getError();
	}

	/**
	 * 设置错误代码
	 * 
	 * @param error_code
	 */
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_code() {
		return error_code;
	}

	/**
	 * 如果没有错误代码，可以抛出原声的错误信息
	 * 
	 * @param oriError
	 */
	public void setOriError(String oriError) {
		this.oriError = oriError;
	}

}
