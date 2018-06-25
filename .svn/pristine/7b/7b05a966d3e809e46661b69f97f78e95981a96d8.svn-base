using System;
using System.Collections.Generic;
using System.Linq;
using DevExpress.Web.ASPxClasses;
using DevExpress.Web.ASPxGridView;
using DevExpress.Web.ASPxTreeView;
using DevExpress.Web.Data;
using Microsoft.Practices.Unity;
using MicroTeam.Common;
using MicroTeam.LumaSystem.IBLL;
using MicroTeam.LumaSystem.Model;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using MicroTeam.DBUtility;
using System.Data;
using MicroTeam.LumaSystem.AppPortal.Sms;
using MicroTeam.LumaSystem.AppPortal.UIHelpers;

namespace MicroTeam.LumaSystem.AppPortal.Sale
{
    public partial class Credit : BasePage<Credit>
    {
        #region property
        [Dependency]
        public IT_Vip_Customer _cust { get; set; }
        [Dependency]
        public IT_Vip_CardServices_Rela _cardService { get; set; }
        [Dependency]
        public IT_Vip_Card _card { get; set; }
        [Dependency]
        public IT_Vip_CarType _carType { get; set; }
        [Dependency]
        public IT_Vip_CardType _cardType { get; set; }
        [Dependency]
        public IT_Vip_CardType_Service _cardTypeService { get; set; }
        [Dependency]
        public IT_Vip_Car _car { get; set; }
        [Dependency]
        public IT_Sal_Consume _consume { get; set; }
        [Dependency]
        public IT_Sal_Consume_Detail _cDetail { get; set; }
        [Dependency]
        public IT_Vip_CarArea _caa { get; set; }
        [Dependency]
        public IT_Emp_Royalty _roy { get; set; }
        #endregion
        public Sal_Consume Consume
        {
            get { return (Sal_Consume)CacheHelper.Current.Get("order_credit_" + UIHelpers.UserProvide.GetCurrentUserName()); }
            set { CacheHelper.Current.Set("order_credit_" + UIHelpers.UserProvide.GetCurrentUserName(), value); }
        }
        #region page_load
        protected void Page_Load(object sender, EventArgs e)
        {
            ASPxTreeView tvNodes = mode_cd.FindControl("tvNodes") as ASPxTreeView;
            List<T_CM_Bank> bankList = new BLL.T_CM_Bank().GetList().Where(p => p.Account == "现金" || p.Account == "挂账" || p.Account == "银行卡" || p.ParentID != "0").ToList();
            UIHelper.TreeViewCreateNodes(bankList, tvNodes.Nodes, "0");
            if (!IsPostBack)
            {
                gvw_CardType.DataSource = _cardType.GetList(new QueryInfo<T_Vip_CardType>());
                gvw_CardType.DataBind();
                //hfd_cust_no.Value = txt_cust_no.Text = InitCustomerCode();
                //品牌
                DataBindCode.DataBindComboBoxColumn(cbb_brand, " para_type='brand'");
                //车型
                //DataBindCode.DataBindComboBoxColumn(cbb_car_type, " para_type='car_type'");
                cbb_car_type.DataSource = _carType.GetList(new QueryInfo<T_Vip_CarType>());
                cbb_car_type.DataBind();
                //价位
                DataBindCode.DataBindComboBoxColumn(cbb_car_sales, " para_type='car_sales'");
                //会员
                glp_cust.DataBind();
                //施工
                gl_Crews.DataBind();
                var caa = _caa.GetList(new QueryInfo<T_Vip_CarArea>().SetQuery("is_top", true));
                cbb_car_area.DataSource = caa;
                cbb_car_area.DataBind();
                var caa_default = caa.FirstOrDefault(p => p.is_default);
                if (caa_default != null)
                    cbb_car_area.Items.FindByValue(caa_default.name).Selected = true;
                //初始数据
                Consume = new Sal_Consume();
                var consume = new T_Sal_Consume(); consume.SetState(EntityState.Added);
                consume.id = Guid.NewGuid();
                consume.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
                consume.acct_no = DateTime.Now.ToString("yyyyMMddHHmmssffffff");
                consume.status_cd = "5";
                consume.order_type = 3;
                consume.add_date = consume.update_date = DateTime.Now;
                consume.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                Consume.consume = consume;
                sdt_sale_amt.Value = consume.sales_amt;
                //sdt_rich_amt.Value = 0;
                sdt_paid_in.Value = consume.paid_in;
                sdt_give_change.Value = consume.give_change;
                txt_acct_no.Value = consume.acct_no;
                if (null != consume.amt_mode_cd)
                {
                    UIHelper.TreeViewSelectedNode(tvNodes, consume.amt_mode_cd);
                }
                if (tvNodes.SelectedNode != null)
                {
                    mode_cd.Text = tvNodes.SelectedNode.Text;
                    mode_cd.KeyValue = tvNodes.SelectedNode.Name;
                }
                else
                {
                    tvNodes.SelectedNode = tvNodes.Nodes[0];
                    mode_cd.Text = tvNodes.SelectedNode.Text;
                    mode_cd.KeyValue = tvNodes.SelectedNode.Name;
                }
                var gvw_car_no = dde_car_no.FindControl("gvw_car_no") as ASPxGridView;
            }
            ASPxWebControl.RegisterBaseScript(Page);
        }
        #endregion
        #region 生成客户编号
        public string InitCustomerCode()
        {
            Guid g = System.Guid.NewGuid();
            string list = g.ToString();
            list = list.Substring(list.IndexOf("-"), 5).ToUpper();
            return "CM" + DateTime.Now.ToString("yyyyMMddHH") + list;
        }
        #endregion
        #region 会员 员工
        public IList<dynamic> CustomerLoad()
        {
            var query = new QueryInfo<T_Vip_Customer>();
            query.SetXml("SelectNoMember");
            return UIHelper.Resolve<IT_Vip_Customer>().Select(query);
        }
        public System.Data.DataSet CrewsSourse()
        {
            return BLL.T_Emp_Info.provide.GetListEmpInfoCode("shop_id='" + UIHelpers.UserProvide.GetCurrentShopGuid() + "' and a.status!=3");
        }
        #endregion
        #region 选择品牌联动车型
        protected void cbb_car_type_Callback(object source, DevExpress.Web.ASPxClasses.CallbackEventArgsBase e)
        {
            if (string.IsNullOrEmpty(e.Parameter)) return;
            var list = _carType.GetList(new QueryInfo<T_Vip_CarType>().SetQuery("brand", e.Parameter));
            cbb_car_type.DataSource = list;
            cbb_car_type.DataBind();
            cbb_car_type.JSProperties.Add("cp_car_type", list.Select(p => p.car_price).ToArray());
        }
        #endregion
        #region 套餐
        protected void gvw_CardTypeService_AfterPerformCallback(object sender, DevExpress.Web.ASPxGridView.ASPxGridViewAfterPerformCallbackEventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            if (e.CallbackName == "APPLYFILTER")
            {
                var queryInfo = new QueryInfo<T_Vip_CardType_Service>();
                Dictionary<string, object> query = JsonConvert.DeserializeObject<Dictionary<string, object>>(e.Args[0].ToString());
                queryInfo.SetQuery(query.ToDictionary(x => x.Key, y => y.Value)).SetQuery("is_order_detail", false).SetQuery("is_free", true);
                grid.DataSource = _cardTypeService.Select(queryInfo);
                grid.DataBind();
            }
        }
        #endregion
        #region 办卡
        protected void cbk_Buy_Callback(object sender, DevExpress.Web.ASPxCallback.CallbackEventArgs e)
        {
            #region 验证是否通过消费
            if ((sdt_account.Number > sdt_paid_in.Number) && mode_cd.Value.ToString()!="挂账")
            {
                e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "收银金额不正确" });
                return;
            }
            if (_card.GetItem(txt_card_no.Text) != null)
            {
                e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "会员卡已经存在" });
                return;
            }
            #endregion
            //更新客户信息
            T_Vip_Customer customer = null;
            if (glp_cust.Text == "")
            {
                customer = new T_Vip_Customer(); customer.SetState(EntityState.Added);
                customer.id = Guid.NewGuid();
                customer.cust_no = UIHelper.InitCustomerCode();
                customer.add_date = DateTime.Now;
            }
            else { customer = _cust.GetItem(Guid.Parse(hfd_cust_id.Value)); customer.SetState(EntityState.Modified); }
            customer.cust_name = txt_cust_name.Text;
            customer.telphone = txt_telphone.Text;
            customer.cert_no = txt_cert_no.Text;
            customer.is_member = true;
            if (cbb_sex_ind.Value != null) customer.sex_ind = (int)cbb_sex_ind.Value;
            if (det_birthday.Value != null) customer.birthday = det_birthday.Date;
            customer.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
            customer.update_date = DateTime.Now;
            customer.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            Consume.customer = customer;
            //增加卡信息
            decimal sale_amt = (decimal)sdt_sale_amt.Value + (sdt_credit_amt.Value == null ? 0 : (decimal)sdt_credit_amt.Value);
            //decimal account = (decimal)sdt_rich_amt.Value + sale_amt;
            var card = new T_Vip_Card(); card.SetState(EntityState.Added);
            card.id = Guid.NewGuid();
            card.cust_id = customer.id;
            Guid cardtype_id = (Guid)gvw_CardType.GetSelectedFieldValues(gvw_CardType.KeyFieldName).First();
            card.cardtype_id = cardtype_id;
            card.card_no = txt_card_no.Text;
            card.card_pwd = txt_card_pwd.Text;
            //card.card_pwd = MicroTeam.Common.DEncrypt.Encrypt(txt_card_pwd.Text);
            card.card_status = true;
            if (det_end_date.Value != null) card.end_date = det_end_date.Date;
            if (det_start_date.Value != null) card.start_date = det_start_date.Date;
            //card.rate = (decimal)sdt_rate.Value;
            card.rich_amt = card.surplus_bal = (decimal)sdt_rich_amt.Value;
            card.add_date = card.update_date = DateTime.Now;
            card.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            #region 积分
            T_Vip_CardType cardType = _cardType.GetItem(cardtype_id);

            if (!card.surplus_integral.HasValue) card.surplus_integral = 0;
            card.surplus_integral += (int)sale_amt * cardType.integral_set;

            #endregion
            Consume.card = card;
            //更新车辆信息
            T_Vip_Car car = null;
            if (string.IsNullOrEmpty(hfd_car_id.Value))
            {
                car = new T_Vip_Car(); car.SetState(EntityState.Added);
                car.id = Guid.NewGuid();
                car.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
                car.cust_id = customer.id; car.add_date = DateTime.Now;
            }
            else { car = _car.GetItem(Guid.Parse(hfd_car_id.Value)); car.SetState(EntityState.Modified); }
            car.car_no = cbb_car_area.Text + txt_car_no.Text;
            if (cbb_brand.Value != null) car.brand = cbb_brand.Value.ToString();
            if (cbb_car_sales.Value != null) car.car_sales =ConvertHelper.ObjectToInt(cbb_car_sales.Value);
            if (cbb_car_type.Value != null) car.car_type = cbb_car_type.Value.ToString();
            car.card_id = card.id;
            car.motor_no = txt_motor_no.Text;
            car.chariotest_no = txt_chariotest_no.Text;
            car.color = txt_color.Text;
            car.num = txt_num.Text;
            if (det_year_date.Value != null)
                car.year_date = det_year_date.Date;
            if (det_insurance_date.Value != null)
                car.insurance_date = det_insurance_date.Date;
            car.update_date = DateTime.Now;
            car.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            string cars = dde_car_no.Text;
            if (!string.IsNullOrEmpty(cars))
            {
                foreach (string car_no in cars.Split(','))
                {
                    var car_1 = _car.GetItem(car_no);
                    if (car_1 != null)
                    {
                        car_1.card_id = card.id;
                        car_1.SetState(EntityState.Modified);
                    }
                    else
                    {
                        car_1 = new T_Vip_Car();
                        car_1.SetState(EntityState.Added);
                        car_1.id = Guid.NewGuid();
                        car_1.car_no = car_no;
                        car_1.card_id = card.id;
                        car_1.cust_id = customer.id;
                        car_1.update_date = car_1.add_date = DateTime.Now;
                        car_1.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                    }
                    Consume.cars.Add(car_1);
                }
            }
            Consume.cars.Add(car);
            //增加卡套餐
            var servers = _cardTypeService.GetList(new QueryInfo<T_Vip_CardType_Service>().SetQuery("cardtype_id", card.cardtype_id.Value));
            List<T_Vip_CardServices_Rela> relas = new List<T_Vip_CardServices_Rela>();
            foreach (var server in servers)
            {
                if (server.is_order_detail.HasValue && !server.is_order_detail.Value && server.consume_cnt.HasValue)
                {
                    T_Vip_CardServices_Rela rela = new T_Vip_CardServices_Rela();
                    rela.SetState(EntityState.Added);
                    rela.card_id = card.id;
                    rela.cnt_rate = server.cnt_rate;
                    rela.surplus_cnt = rela.consume_cnt = server.consume_cnt;
                    rela.is_free = server.is_free;
                    rela.pro_id = server.pro_id;
                    rela.add_date = rela.update_date = DateTime.Now;
                    rela.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                    relas.Add(rela);
                }
            }
            Consume.cardServices = relas;
            //增加订单明细
            var detail = new T_Sal_Consume_Detail(); detail.SetState(EntityState.Added);
            detail.id = Guid.NewGuid();
            detail.account_det = sale_amt;
            detail.car_id = car.id;
            detail.card_amt = 0;
            if (sdt_handsel_amt.Value != null)
                detail.cash_coupon = (decimal)sdt_handsel_amt.Value;
            var odServer = servers.FirstOrDefault(p => p.is_order_detail.Value);
            if (odServer != null)
            {
                var pro = BLL.T_Prd_Info.provide.GetModel(odServer.pro_id.Value);
                if (pro != null)
                {
                    detail.consume_type_cd = pro.service_as;
                    detail.pro_id = pro.id;
                    detail.unit_amt = pro.sales_amt ?? 0;
                }
            }

            detail.consume_type_cd = "package";
            ///detail.pro_id = null;
            detail.unit_amt = Convert.ToDecimal(sdt_sale_amt.Value);
            detail.mode_cd = Convert.ToString(mode_cd.KeyValue);
            detail.number = 1;
            detail.order_id = Consume.consume.id;
            detail.rate = 10;
            detail.remarks = txt_remarks.Text;
            decimal sum_bal = (decimal)sdt_sum_bal.Value;
            detail.sum_bal = detail.cope_amt = sum_bal;
            detail.add_date = detail.update_date = DateTime.Now;
            detail.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            Consume.details.Add(detail);
            #region 销售、施工、提成
            List<object> crew_ids = gl_Crews.GridView.GetSelectedFieldValues(gl_Crews.GridView.KeyFieldName);
            decimal account_det = 0, account_royalty = 0;
            decimal costunit = detail.cost * detail.number;
            account_det = account_royalty = (decimal)detail.account_det + (decimal)detail.card_amt;
            if (crew_ids.Count > 0)
            {
                account_det = account_det / crew_ids.Count;
                costunit = costunit / crew_ids.Count;
            }
            foreach (Guid crew in crew_ids)
            {
                T_Sal_Consume_Crew crewItem = new T_Sal_Consume_Crew(); crewItem.SetState(EntityState.Added);
                crewItem.id = Guid.NewGuid();
                crewItem.consume_id = detail.id;
                crewItem.emp_id = crew;
                crewItem.royalty_type = 3;
                RoyaltyHelper.Royalty(crew, detail, account_royalty, crew_ids.Count, crewItem);
                //Royalty(crew, detail, account_royalty, crew_ids.Count, crewItem);
                crewItem.consume_amt = account_det;
                crewItem.gross_amt = account_det - costunit;
                crewItem.add_date = crewItem.update_date = DateTime.Now;
                crewItem.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                Consume.crews.Add(crewItem);
            }
            #endregion
            //订单信息
            Consume.consume.cust_id = customer.id;
            Consume.consume.account = Consume.consume.cash = sale_amt;
            Consume.consume.sales_amt = sum_bal;
            Consume.consume.paid_in = (decimal)sdt_paid_in.Value;//实收
            Consume.consume.give_change = (decimal)sdt_give_change.Value;//找钱
            Consume.consume.remarks = txt_remarks.Text;
            Consume.consume.amt_mode_cd = Convert.ToString(mode_cd.KeyValue);
            Consume.consume.status_cd = "1";
            Consume.consume.card_id = card.id;
            Consume.consume.card_no = card.card_no;
            //提交办卡订单
            car_nos.Clear();
            int result = _consume.Batch(Consume);
            if (result > 0) e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = true, msg = "结算成功", id = Consume.consume.id });
            else e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "提交失败" });

            //增加短信发送功能（吴，2013.9.26）
            if (e.Result.IndexOf("结算成功") >= 0)
            {
                try
                {
                    string jsonText = "[" + e.Result.ToString() + "]";
                    JArray ja = (JArray)JsonConvert.DeserializeObject(jsonText);
                    JObject o = (JObject)ja[0];
                    string order_id = o["id"].ToString().Replace("\"", "");  //订单编号
                    DataSet ds_order = DbHelperSQL.Query("select * from T_Sal_Consume_Detail where order_id='" + order_id + "'");
                    if (ds_order.Tables[0].Rows.Count > 0)
                    {
                        Decimal unit_cmt = Decimal.Parse(ds_order.Tables[0].Rows[0]["unit_amt"].ToString()); //办卡金额（套餐金额）
                        Decimal cash_coupon = Decimal.Parse(ds_order.Tables[0].Rows[0]["cash_coupon"].ToString());//充值赠送金额
                        Decimal account_det1 = Decimal.Parse(ds_order.Tables[0].Rows[0]["account_det"].ToString()); //实收金额
                        Decimal cz = account_det1 - unit_cmt;   //充值金额（实收金额-办卡金额）
                        string car_id = ds_order.Tables[0].Rows[0]["car_id"].ToString();
                        DataSet ds_car = DbHelperSQL.Query("select * from T_Vip_Car where id='" + car_id + "'");
                        if (ds_car.Tables[0].Rows.Count > 0)
                        {
                            string cust_id = ds_car.Tables[0].Rows[0]["cust_id"].ToString(); //对应T_Vip_Customer表id
                            string shop_id = ds_car.Tables[0].Rows[0]["shop_id"].ToString(); //对应T_Cm_Shop表id
                            string card_id = ds_car.Tables[0].Rows[0]["card_id"].ToString(); //对应T_Vip_Card表id
                            string car_no1 = ds_car.Tables[0].Rows[0]["car_no"].ToString();   //车牌号码
                            DataSet ds_cust = DbHelperSQL.Query("select * from T_Vip_Customer where id='" + cust_id + "'");
                            DataSet ds_card = DbHelperSQL.Query("select * from T_Vip_Card where id='" + card_id + "'");
                            if ((ds_cust.Tables[0].Rows.Count > 0) && (ds_card.Tables[0].Rows.Count > 0))
                            {
                                DataSet ds_cardtype = DbHelperSQL.Query("select * from T_Vip_CardType where id='" + ds_card.Tables[0].Rows[0]["cardtype_id"].ToString() + "'");
                                DataSet ds_shop = DbHelperSQL.Query("select * from T_Cm_Shop where id='" + shop_id + "'");
                                string tel = ds_cust.Tables[0].Rows[0]["telphone"].ToString();//手机号码
                                string cust_name = ds_cust.Tables[0].Rows[0]["cust_name"].ToString();//客户姓名
                                string content = "恭喜您从即刻起成为我们“" + ds_cardtype.Tables[0].Rows[0]["type_name"].ToString() + "”会员；";
                                if (unit_cmt > 0)
                                {
                                    content += "购买套餐：" + unit_cmt.ToString() + "元，";
                                }
                                if (cz > 0)
                                {
                                    content += "充值：" + cz.ToString() + "元，";
                                }
                                if (cash_coupon > 0)
                                {
                                    content += "充值赠送：" + cash_coupon.ToString() + "元";
                                }
                                content += "[" + ds_shop.Tables[0].Rows[0]["shop_name"].ToString() + "]";
                                Model.T_Sms_Result model = new Model.T_Sms_Result();
                                model.template_id = car_id;
                                model.model = "carsale";
                                model.model_type = "Credit";
                                model.sms_date = DateTime.Now;
                                model.sms_content = content;
                                model.cust_name = cust_name;
                                model.telphone = tel;
                                model.car_no = car_no1;
                                model.shop_id = shop_id;
                                SendSms.sendMessage(model);
                            }
                        }

                    }
                }
                catch
                {

                }
            }

        }
        #endregion
        //#region 提成计算
        //private void Royalty(Guid crew, T_Sal_Consume_Detail detail, decimal account_royalty, int emp_count, T_Sal_Consume_Crew crewItem)
        //{
        //    decimal account_det = account_royalty / emp_count;
        //    var emp_info = BLL.T_Emp_Info.provide.GetModel(crew);
        //    if (emp_info == null) { crewItem.royalty = 0; crewItem.royalty_amt = 0; }
        //    else
        //    {
        //        T_Emp_Royalty roy_pro = _roy.GetItem(new QueryInfo<T_Emp_Royalty>()
        //            .SetQuery("consume_type_cd", detail.consume_type_cd).SetQuery("emp_type_cd", emp_info.emp_type_cd)
        //            .SetQuery("account_det", account_det).SetQuery("pro_id", detail.pro_id));
        //        if (roy_pro == null) roy_pro = _roy.GetItem(new QueryInfo<T_Emp_Royalty>()
        //                .SetQuery("consume_type_cd", detail.consume_type_cd)
        //                .SetQuery("account_det", account_det).SetQuery("pro_id", detail.pro_id));

        //        if (roy_pro != null)
        //        {
        //            crewItem.royalty = roy_pro.royalty; crewItem.is_fix = roy_pro.is_fix;
        //            if (UIHelper.IsBool(roy_pro.is_fix)) crewItem.royalty_amt = roy_pro.royalty;
        //            else crewItem.royalty_amt = roy_pro.royalty * (decimal)0.01 * account_det;
        //        }
        //        else { crewItem.royalty = 0; crewItem.royalty_amt = 0; }
        //    }
        //}
        //#endregion
        #region 绑定车辆
        public List<T_Vip_Car> BindCar_nos()
        {
            return car_nos;
        }
        public IList<T_Vip_CarArea> BindCarArea()
        {
            return UIHelper.Resolve<IT_Vip_CarArea>().GetList(new QueryInfo<T_Vip_CarArea>().SetQuery("is_top", true));
        }
        public static List<T_Vip_Car> car_nos = new List<T_Vip_Car>();
        protected void gvw_car_no_CustomJSProperties(object sender, ASPxGridViewClientJSPropertiesEventArgs e)
        {
            ASPxGridView grid = (ASPxGridView)dde_car_no.FindControl("gvw_car_no");
            string cpcar_nos = string.Empty;
            for (int i = 0; i < grid.VisibleRowCount; i++)
            {
                cpcar_nos += grid.GetRowValues(i, "car_no") + ",";
            }
            e.Properties["cpcar_nos"] = cpcar_nos.TrimEnd(',');
        }
        #region 增加、编辑、删除车辆
        protected void gvw_car_no_OnRowInserting(object sender, ASPxDataInsertingEventArgs e)
        {
            ASPxGridView grid = sender as ASPxGridView;
            e.NewValues["id"] = Guid.NewGuid();
            var cbb_car_area = grid.FindEditFormTemplateControl("cbb_car_area") as DevExpress.Web.ASPxEditors.ASPxComboBox;
            var txt_car_no = grid.FindEditFormTemplateControl("txt_car_no") as DevExpress.Web.ASPxEditors.ASPxTextBox;
            e.NewValues["car_no"] = cbb_car_area.Text + txt_car_no.Text;
        }
        protected void gvw_car_no_OnRowUpdating(object sender, ASPxDataUpdatingEventArgs e)
        {
            ASPxGridView grid = sender as ASPxGridView;
            var cbb_car_area = grid.FindEditFormTemplateControl("cbb_car_area") as DevExpress.Web.ASPxEditors.ASPxComboBox;
            var txt_car_no = grid.FindEditFormTemplateControl("txt_car_no") as DevExpress.Web.ASPxEditors.ASPxTextBox;
            e.NewValues["car_no"] = cbb_car_area.Text + txt_car_no.Text;
        }
        public void addCar_nos(T_Vip_Car car)
        {
            car_nos.RemoveAll((m) => { return m.car_no == car.car_no; });
            car_nos.Add(car);
        }
        public void editCar_nos(T_Vip_Car car)
        {
            car_nos.RemoveAll((m) => { return m.id == car.id || m.car_no == car.car_no; });
            car_nos.Add(car);
        }
        public void deleteCar_nos(T_Vip_Car car)
        {
            car_nos.RemoveAll((m) => { return m.id == car.id; });
        }
        #endregion
        #endregion
    }
}