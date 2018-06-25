using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text.RegularExpressions;
using System.Web.UI.WebControls;
using DevExpress.Web.ASPxEditors;
using DevExpress.Web.ASPxGridView;
using DevExpress.Web.ASPxTreeView;
using Microsoft.Practices.Unity;
using MicroTeam.Common;
using MicroTeam.DBUtility;
using MicroTeam.LumaSystem.AppPortal.Sms;
using MicroTeam.LumaSystem.AppPortal.UIHelpers;
using MicroTeam.LumaSystem.IBLL;
using MicroTeam.LumaSystem.Model;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;


namespace MicroTeam.LumaSystem.AppPortal.Sale
{
    public partial class Buy : BasePage<Buy>
    {
        #region
        BLL.T_CM_Bank bllBank = new BLL.T_CM_Bank();
        [Dependency]
        public IDao _dao { get; set; }
        [Dependency]
        public IT_Vip_Card _card { get; set; }
        [Dependency]
        public IT_Vip_CarType _carType { get; set; }
        [Dependency]
        public IT_Vip_Car _car { get; set; }
        [Dependency]
        public IT_Vip_Customer _cust { get; set; }
        [Dependency]
        public IT_Vip_CardServices_Rela _cardService { get; set; }
        [Dependency]
        public IT_Vip_CardType_SaleType _cardSaleType { get; set; }
        [Dependency]
        public IT_Vip_CardType_Service _cardTypeService { get; set; }
        [Dependency]
        public IT_Sal_Consume _consume { get; set; }
        [Dependency]
        public IT_Sal_Consume_Crew _cCrew { get; set; }
        [Dependency]
        public IT_Sal_Consume_Detail _cDetail { get; set; }
        [Dependency]
        public IT_Vip_CarArea _caa { get; set; }
        [Dependency]
        public IT_Vip_Acct _acct { get; set; }
        [Dependency]
        public IT_Emp_Royalty _roy { get; set; }

        #endregion
        public Sal_Consume Consume
        {
            get { return (Sal_Consume)CacheHelper.Current.Get("order_" + UIHelpers.UserProvide.GetCurrentUserName() + "_" + (id ?? Guid.Empty)); }
            set { CacheHelper.Current.Set("order_" + UIHelpers.UserProvide.GetCurrentUserName() + "_" + (id ?? Guid.Empty), value); }
        }
        //采用URL传来的结算状态，判断消费单的结算状态类型,默认1未结算
        public Guid? id = UIHelper.Get<Guid?>("id");
        #region page_load
        protected void Page_Load(object sender, EventArgs e)
        {
            if (gvw_Product.IsCallback)
            {
                gvw_Product.JSProperties["cp_selectValue"] = "";
                List<object> table = gvw_Product.GetSelectedFieldValues(new string[] { "id", "stock_id" });
                string msg = "";
                foreach (object[] obj in table)
                {
                    msg += ',' + obj[0].ToString() + ',' + obj[1].ToString();

                    if (msg != "" && sdt_surplus_bal.Value != null)
                    {
                        msg = msg + "," + sdt_surplus_bal.Value;
                    }
                    else
                    {
                        msg = msg + ",0.0";
                    }
                }

                gvw_Product.JSProperties["cp_selectValue"] = msg;

                gvw_Product.Selection.UnselectAll();
            }


            if (IsPostBack) return;
            //DataBindCode.DataBindComboBoxColumn(mode_cd, " para_type='count_mode'");
            //DataBindCode.DataBindComboBoxColumn(cbb_status_cd, " para_type='count_status'");
            //品牌
            DataBindCode.DataBindComboBoxColumn(cbb_brand, " para_type='brand'");
            //车型
            //DataBindCode.DataBindComboBoxColumn(cbb_car_type, " para_type='car_type'");
            cbb_car_type.DataSource = _carType.GetList(new QueryInfo<T_Vip_CarType>().SetQuery("OrderBy", "sort"));
            cbb_car_type.DataBind();
            //价位
            DataBindCode.DataBindComboBoxColumn(cbb_car_sales, " para_type='car_sales'");
            var caa = _caa.GetList(new QueryInfo<T_Vip_CarArea>().SetQuery("is_top", true));
            cbb_car_area.DataSource = caa;
            cbb_car_area.DataBind();
            var caa_default = caa.FirstOrDefault(p => p.is_default);
            if (caa_default != null)
                cbb_car_area.Items.FindByValue(caa_default.name).Selected = true;
            //glp_cust.DataBind();
            Session[Session.SessionID + "ServicesType"] = string.Format(" service_as!='charge' and service_as!='package' ");
            //gvw_Product.FilterExpression = "[prd_type_cd_name] = '洗车'";

            ods_Product.Select();
            gvw_Product.DataBind();

            //Session[Session.SessionID + "ServicesType"] = string.Format(" service_as='{0}'", "carwash"); 
            //gvw_Product.FilterExpression = "";

            //仓库
            string inshop = " [shop_id]='" + UIHelpers.UserProvide.GetCurrentUser().def_show_shop + "'";
            BLL.T_Prd_StoreHouse storebll = new BLL.T_Prd_StoreHouse();
            DataSet storeDs = storebll.GetList(inshop);
            for (int i = 0; i < storeDs.Tables[0].Rows.Count; i++)
            {
                //ASPxPageControl.TabPages.Add(storeDs.Tables[0].Rows[i]["name"].ToString(),
                //    storeDs.Tables[0].Rows[i]["id"].ToString() + i, "");
                //storeids += string.Format("store_id='{0}' or ", storeDs.Tables[0].Rows[i]["id"].ToString());
                ListEditItem item = new ListEditItem();
                item.Text = storeDs.Tables[0].Rows[i]["name"].ToString();
                item.Value = storeDs.Tables[0].Rows[i]["id"].ToString();
                cmb_store_id.Items.Add(item);

            }
            cmb_store_id.Items.Insert(0, new ListEditItem("全部仓库", Guid.Empty.ToString()));
            cmb_store_id.SelectedIndex = 0;
            FristBind();




        }
        #endregion
        #region 初始数据
        private void FristBind()
        {
            Consume = new Sal_Consume();
            var consume = new T_Sal_Consume();

            ASPxTreeView tvNodes = cbb_mode_cd.FindControl("tvNodes") as ASPxTreeView;
            UIHelper.TreeViewCreateNodes(new BLL.T_CM_Bank().GetList(), tvNodes.Nodes, "0");
            if (id.HasValue)
            {
                consume = _consume.GetItem(id.Value);
                dynamic cust = _cust.GetItem(new QueryInfo<T_Vip_Customer>().SetQuery("id", consume.cust_id));
                glp_cust.Value = cust.car_id;
                hfd_cust_id.Value = cust.id.ToString();
                if (cust.cust_name != null) txt_cust_name.Value = cust.cust_name;
                if (cust.car_no != null)
                {
                    try
                    {
                        cbb_car_area.Items.FindByValue(cust.car_no.Substring(0, 2)).Selected = true;
                        txt_car_no.Value = cust.car_no.Substring(2);
                    }
                    catch
                    {

                    }
                }
                if (cust.telphone != null) txt_telphone.Value = cust.telphone;
                if (cust.car_id != null) hfd_car_id.Value = cust.car_id.ToString();
                if (cust.surplus_bal != null) { sdt_surplus_bal.Value = sdt_surplus_bal1.Value = cust.surplus_bal.ToString(); }
                if (cust.brand != null) cbb_brand.Value = cust.brand;
                if (cust.car_type != null) cbb_car_type.Value = cust.car_type;
                if (cust.car_sales != null) cbb_car_sales.Value = cust.car_sales;
                if (cust.km_num != null) sdt_km_num.Value = cust.km_num;
                if (consume.card_id.HasValue)
                {
                    Consume.card = _card.GetItem(consume.card_id);
                    hfd_card_id.Value = consume.card_id.ToString();
                    hfd_card_no.Value = txt_card_no.Text = Consume.card.card_no;
                    Consume.cardSaleTypes = _cardSaleType.GetList(new QueryInfo<T_Vip_CardType_SaleType>().SetQuery("cardtype_id", Consume.card.cardtype_id)).ToList();
                    Consume.cardType_Services = _cardTypeService.GetList(new QueryInfo<T_Vip_CardType_Service>().SetQuery("cardtype_id", Consume.card.cardtype_id).SetQuery("is_free", false).SetQuery("is_order_detail", false)).ToList();
                    Consume.cardServices = _cardService.GetList(new QueryInfo<T_Vip_CardServices_Rela>().SetQuery("card_id", consume.card_id).SetQuery("is_free", true)).ToList();
                    Consume.readCard = true;
                    Dictionary<string, object> dicCs = new Dictionary<string, object>();
                    dicCs.Add("card_id", consume.card_id);
                    BindCardGridService(gvw_CardServices, dicCs);
                }
            }
            else
            {
                consume.id = Guid.NewGuid();
                consume.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
                consume.acct_no = DateTime.Now.ToString("yyyyMMddHHmmssffffff");
                consume.status_cd = "5";
                consume.order_type = 1;
                consume.add_date = consume.update_date = DateTime.Now;
                consume.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            }
            UIHelper.TreeViewSelectedNode(tvNodes, consume.amt_mode_cd);
            if (tvNodes.SelectedNode != null)
            {
                cbb_mode_cd.Text = tvNodes.SelectedNode.Text;
                cbb_mode_cd.KeyValue = tvNodes.SelectedNode.Name;
            }
            else
            {
                tvNodes.SelectedNode = tvNodes.Nodes[0];
                cbb_mode_cd.Text = tvNodes.SelectedNode.Text;
                cbb_mode_cd.KeyValue = tvNodes.SelectedNode.Name;
            }
            Consume.consume = consume;
            //sdt_sales_amt.Value = consume.sales_amt;
            sdt_paid_in.Value = consume.paid_in;
            sdt_give_change.Value = consume.give_change;
            sdt_account.Value = consume.account;
            txt_acct_no.Text = consume.acct_no;
            BindOrderDetail(gvw_OrderDetail);
        }
        #endregion
        #region 商品绑定查询
        protected void ods_Product_OnSelecting(object sender, ObjectDataSourceSelectingEventArgs e)
        {
            if (gvw_Product.IsCallback || !IsPostBack)
            {
                if (!string.IsNullOrEmpty(gvw_Product.FilterExpression))
                {
                    string likeName = string.Empty;
                    Session["name_filter"] = null;
                    if (gvw_Product.FilterExpression.Contains("[service_as_name] = '洗车'"))
                        e.InputParameters["strWhere"] = " service_as='carwash'";
                    else
                    {
                        gvw_Product.FilterExpression = Regex.Replace(gvw_Product.FilterExpression, @"(Contains)\(\[(name)\], '(.*?)'\)", (match) =>
                        {
                            Session["name_filter"] = match.Groups[3].Value;
                            likeName = string.Format(" and (a.name like '%{0}%' or a.short_name like '%{0}%')", match.Groups[3].Value);
                            return string.Empty;
                        });

                        e.InputParameters["strWhere"] = Convert.ToString(Session[Session.SessionID + "ServicesType"]) + likeName; ;
                        e.InputParameters["shop_id"] = UIHelpers.UserProvide.GetCurrentShopId();
                        if (Session[Session.SessionID + "Store"] != null)
                        {
                            e.InputParameters["store_id"] = Convert.ToString(Session[Session.SessionID + "Store"]);
                        }
                        else
                        {
                            e.InputParameters["store_id"] = "";
                        }

                        // e.InputParameters["strWhere"] = " service_as!='charge' and service_as!='package' and (d.shop_id='" + UIHelpers.UserProvide.GetCurrentShopId() + "' and d.number>0) or is_service=1"  + likeName;
                    }
                }
                else
                {
                    Session["name_filter"] = null;
                    e.InputParameters["strWhere"] = Convert.ToString(Session[Session.SessionID + "ServicesType"]);
                    e.InputParameters["shop_id"] = UIHelpers.UserProvide.GetCurrentShopId();
                    if (Session[Session.SessionID + "Store"] != null)
                    {
                        e.InputParameters["store_id"] = Convert.ToString(Session[Session.SessionID + "Store"]);
                    }
                    else
                    {
                        e.InputParameters["store_id"] = "";
                    }
                }

                //string likeName = string.Empty;
                //gvw_Product.FilterExpression = Regex.Replace(gvw_Product.FilterExpression, @"(Contains)\(\[(name)\], '(.*?)'\)", (match) =>
                //{
                //    Session["name_filter"] = match.Groups[3].Value;
                //    likeName = string.Format(" and (a.name like '%{0}%' or a.short_name like '%{0}%')", match.Groups[3].Value);
                //    return string.Empty;
                //});

                //e.InputParameters["strWhere"] = Convert.ToString(Session[Session.SessionID + "ServicesType"]) + likeName; ;
                //e.InputParameters["shop_id"] = UIHelpers.UserProvide.GetCurrentShopId();

            }
            else
            {
                e.Cancel = true;
            }
        }
        #endregion
        #region 订单明细绑定
        protected void BindOrderDetail(DevExpress.Web.ASPxGridView.ASPxGridView grid)
        {
            decimal cp_total = 0, cp_sales = 0, cp_account = 0, cp_cash = 0, cp_card = 0; int cp_rowCount = 0;
            foreach (dynamic dyn in Consume.consume.Consume_Detail)
            {
                cp_total += dyn.sum_bal;
                cp_sales += dyn.cope_amt;
                cp_account += dyn.account_det;
                cp_cash += dyn.account_det;
                cp_card += dyn.card_amt;
                cp_rowCount++;
            }
            Consume.consume.account = cp_account;
            sdt_total_amt.Value = cp_total;
            grid.JSProperties.Add("cp_total", cp_total);
            grid.JSProperties.Add("cp_sales", cp_sales);
            grid.JSProperties.Add("cp_account", cp_account);
            grid.JSProperties.Add("cp_cash", cp_cash);
            grid.JSProperties.Add("cp_card", cp_card);
            grid.JSProperties.Add("cp_rowCount", cp_rowCount);
            grid.DataSource = Consume.consume.Consume_Detail;
            grid.DataBind();
        }
        #endregion
        #region 客户/会员卡/员工
        public IList<dynamic> CustomerLoad()
        {
            #region 加缓存会出现，新增的客户同步不过来。
            //IList<dynamic> custList = null;
            //if( Session["custList_buy"]==null ){
            //    custList = UIHelper.Resolve<IT_Vip_Customer>().Select(new QueryInfo<T_Vip_Customer>());
            //    Session["custList_buy"] = custList;
            //}else{
            //    custList = (IList<dynamic>)Session["custList_buy"];
            //}
            //return custList;//可以结合ObjectDataSource一起使用
            #endregion

            return UIHelper.Resolve<IT_Vip_Customer>().Select(new QueryInfo<T_Vip_Customer>());//可以结合ObjectDataSource一起使用
        }
        public System.Data.DataSet CrewsSourse()
        {
            return BLL.T_Emp_Info.provide.GetListEmpInfoCode("shop_id='" + UIHelpers.UserProvide.GetCurrentShopGuid() + "' and a.status!=3");
        }
        public List<T_Cm_Code> Getemp_type_cd()
        {
            MicroTeam.LumaSystem.BLL.T_Cm_Code code = new BLL.T_Cm_Code();
            return code.GetModelList(" para_type='emp_type_cd'");
        }
        public List<T_Cm_Shop> GetShopName()
        {
            MicroTeam.LumaSystem.BLL.T_Cm_Shop code = new BLL.T_Cm_Shop();
            return code.GetModelList(""); ;
        }

        #endregion
        #region 套餐绑定
        private void BindCardGridService(DevExpress.Web.ASPxGridView.ASPxGridView grid, object card_id_Filter)
        {
            object end_date = glp_cust.GridView.GetRowValues(glp_cust.GridView.FocusedRowIndex, "end_date");
            if (end_date != null && DateTime.Compare(DateTime.Now, DateTime.Parse(end_date.ToString())) > 0)
            {
                gvw_CardServices.SettingsText.EmptyDataRow = "会员过期，无法使用套餐！";
                grid.DataSource = null;
                grid.DataBind();
                return;
            }
            var queryInfo = new QueryInfo<T_Vip_CardServices_Rela>();
            queryInfo.SetQuery("is_free", true).SetQuery("is_valid", 1);
            if (card_id_Filter.GetType() != typeof(string))
            {
                queryInfo.SetQuery((Dictionary<string, object>)card_id_Filter);
                grid.DataSource = _cardService.Select(queryInfo);
                grid.DataBind();
            }
            else
            {
                Dictionary<string, object> query = JsonConvert.DeserializeObject<Dictionary<string, object>>(card_id_Filter.ToString());
                if (query["card_id"] == null || query["card_id"].ToString() == string.Empty) grid.DataSource = null;
                else
                {
                    queryInfo.SetQuery(query.ToDictionary(x => x.Key, y => y.Value));
                    grid.DataSource = _cardService.Select(queryInfo);
                    grid.DataBind();
                }
            }
        }
        protected void gvw_CardServices_AfterPerformCallback(object sender, DevExpress.Web.ASPxGridView.ASPxGridViewAfterPerformCallbackEventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            if (e.CallbackName == "APPLYFILTER")
                BindCardGridService(grid, e.Args[0]);
        }
        #endregion
        #region 编辑保存订单明细
        #region 编辑订单明细所绑定数据
        protected void gvw_OrderDetail_HtmlEditFormCreated(object sender, DevExpress.Web.ASPxGridView.ASPxGridViewEditFormEventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            //var edit_mode_cd = grid.FindEditFormTemplateControl("mode_cd") as DevExpress.Web.ASPxEditors.ASPxComboBox;
            //DataBindCode.DataBindComboBoxColumn(edit_mode_cd, " para_type='count_mode'");
            //var consume_type_cd = grid.FindEditFormTemplateControl("consume_type_cd") as DevExpress.Web.ASPxEditors.ASPxComboBox;
            //DataBindCode.DataBindComboBoxColumn(consume_type_cd, " para_type='service_as'");
            var controlCrews = grid.FindEditFormTemplateControl("Crews") as DevExpress.Web.ASPxGridLookup.ASPxGridLookup;
            var crew_ids = grid.GetRowValues(grid.EditingRowVisibleIndex, "Crews");
            if (crew_ids != null && !string.IsNullOrEmpty(crew_ids.ToString()))
            {
                foreach (var tagID in crew_ids.ToString().Split(','))
                    controlCrews.GridView.Selection.SelectRowByKey(new Guid(tagID));
            }
            var controlCrewsSale = grid.FindEditFormTemplateControl("CrewsSale") as DevExpress.Web.ASPxGridLookup.ASPxGridLookup;
            var crewsSale_ids = grid.GetRowValues(grid.EditingRowVisibleIndex, "CrewsSale");
            if (crewsSale_ids != null && !string.IsNullOrEmpty(crewsSale_ids.ToString()))
            {
                foreach (var tagID in crewsSale_ids.ToString().Split(','))
                    controlCrewsSale.GridView.Selection.SelectRowByKey(new Guid(tagID));
            }
            var edit_mode_cd = grid.FindEditFormTemplateControl("mode_cd") as ASPxDropDownEdit;
            ASPxTreeView tvNodes = edit_mode_cd.FindControl("tvNodes") as ASPxTreeView;
            UIHelper.TreeViewCreateNodes(new BLL.T_CM_Bank().GetList(), tvNodes.Nodes, "0");
            if (!grid.IsNewRowEditing)
            {
                string modecd = Convert.ToString(grid.GetRowValues(grid.EditingRowVisibleIndex, new string[] { "mode_cd" }));
                UIHelper.TreeViewSelectedNode(tvNodes, modecd);
            }
            if (tvNodes.SelectedNode != null)
            {
                edit_mode_cd.Text = tvNodes.SelectedNode.Text;
                edit_mode_cd.KeyValue = tvNodes.SelectedNode.Name;
            }
            else
            {
                tvNodes.SelectedNode = tvNodes.Nodes[0];
                edit_mode_cd.Text = tvNodes.SelectedNode.Text;
                edit_mode_cd.KeyValue = tvNodes.SelectedNode.Name;
            }
        }


        protected void gvw_OrderDetail_DataBound(object sender, EventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            var edit_mode_cd = grid.FindEditFormTemplateControl("mode_cd") as ASPxDropDownEdit;
            ASPxTreeView tvNodes = edit_mode_cd.FindControl("tvNodes") as ASPxTreeView;
            var hfModecd = grid.FindEditFormTemplateControl("hfModecd") as HiddenField;
            UIHelper.TreeViewSelectedNode(tvNodes, hfModecd.Value);
            if (tvNodes.SelectedNode != null)
            {
                cbb_mode_cd.Text = tvNodes.SelectedNode.Text;
                cbb_mode_cd.KeyValue = tvNodes.SelectedNode.Name;
            }
            else
            {
                tvNodes.SelectedNode = tvNodes.Nodes[0];
                cbb_mode_cd.Text = tvNodes.SelectedNode.Text;
                cbb_mode_cd.KeyValue = tvNodes.SelectedNode.Name;
            }
        }
        #endregion
        #region 编辑保存
        protected void gvw_OrderDetail_RowUpdating(object sender, DevExpress.Web.Data.ASPxDataUpdatingEventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            dynamic model = Consume.consume.Consume_Detail.FirstOrDefault(p => p.id == new Guid(e.Keys[0].ToString()));
            UIHelpers.UpdateDynamic.ToUpdateDynamic(model, grid);
            var controlCrews = grid.FindEditFormTemplateControl("Crews") as DevExpress.Web.ASPxGridLookup.ASPxGridLookup;
            var controlCrewsSale = grid.FindEditFormTemplateControl("CrewsSale") as DevExpress.Web.ASPxGridLookup.ASPxGridLookup;
            var cbb_mode_cd = grid.FindEditFormTemplateControl("mode_cd") as ASPxDropDownEdit;
            var txt_pro_name = grid.FindEditFormTemplateControl("pro_name") as ASPxTextBox;
            model.mode_cd = cbb_mode_cd.KeyValue;
            List<object> crew_ids = controlCrews.GridView.GetSelectedFieldValues(controlCrews.GridView.KeyFieldName);
            List<object> crewSale_ids = controlCrewsSale.GridView.GetSelectedFieldValues(controlCrewsSale.GridView.KeyFieldName);
            model.Crews = crew_ids.Count > 0 ? crew_ids.Aggregate((j, k) => j.ToString() + "," + k.ToString()).ToString() : string.Empty;
            model.CrewsSale = crewSale_ids.Count > 0 ? crewSale_ids.Aggregate((j, k) => j.ToString() + "," + k.ToString()).ToString() : string.Empty;
            model.Crews_Name = controlCrews.Text;
            model.CrewsSale_Name = controlCrewsSale.Text;

            float sss = (float)model.number;
            model.cope_amt = (decimal)model.unit_amt * (decimal)model.number;
            model.account_det = DetailAmt(model);
            if (cbb_mode_cd.KeyValue.ToString() == "3")
                model.sum_bal = (decimal)model.unit_amt * (decimal)model.number * (decimal)model.rate * (decimal)0.1 - (decimal)model.cash_coupon;
            else
                model.sum_bal = (decimal)model.unit_amt * (decimal)model.number;
            model.mode_cd_name = cbb_mode_cd.Text;
            model.pro_name = txt_pro_name.Text;
            e.Cancel = true;
            grid.CancelEdit();
            BindOrderDetail(grid);
        }
        #endregion
        protected void cbb_car_type_Callback(object source, DevExpress.Web.ASPxClasses.CallbackEventArgsBase e)
        {
            if (string.IsNullOrEmpty(e.Parameter)) return;
            var list = _carType.GetList(new QueryInfo<T_Vip_CarType>().SetQuery("brand", e.Parameter).SetQuery("OrderBy", "sort"));
            cbb_car_type.DataSource = list;
            cbb_car_type.DataBind();
            cbb_car_type.JSProperties.Add("cp_car_type", list.Select(p => p.car_price).ToArray());
        }
        #endregion
        #region 新增、删除、刷新、清空、重新计算订单明细
        protected void gvw_OrderDetail_CustomCallback(object sender, DevExpress.Web.ASPxGridView.ASPxGridViewCustomCallbackEventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            string[] parameters = e.Parameters.Split(',');
            gvw_OrderDetail.JSProperties["cp_add"] = "";
            if (parameters[0] == "Delete")
            {
                List<object> ids = grid.GetSelectedFieldValues("id");
                foreach (object id in ids)
                {
                    Consume.consume.Consume_Detail.RemoveAll((detail) =>
                    {
                        bool ishas = detail.id == new Guid(id.ToString());
                        if (ishas)
                        {
                            if (detail.state == "edit")
                            {
                                var model = new T_Sal_Consume_Detail(); model.SetState(EntityState.Deleted); model.id = detail.id;
                                Consume.details.Add(model);
                            }
                            //用了会员卡，要还卡里的钱和服务次数给客户
                            if (Consume.card != null && detail.mode_cd == "2")
                            {
                                var cardService = Consume.cardServices.FirstOrDefault(p => p.pro_id == detail.pro_id);
                                if (cardService != null)
                                {
                                    if (cardService.consume_cnt.HasValue && cardService.surplus_cnt.HasValue)
                                        cardService.surplus_cnt = cardService.surplus_cnt + detail.number;
                                }
                                //被减去金额重新加回去，旧金额参数要初始化
                                Consume.card.surplus_bal = Consume.card.surplus_bal + detail.card_amt;
                                Consume.card.consume_amt = Consume.card.consume_amt - detail.card_amt;
                            }
                        }
                        return ishas;
                    });
                }
                BindOrderDetail(grid);
            }
            else if (e.Parameters == "Clear")
            {
                Consume.details.ForEach((detail) =>
                {
                    if (detail.GetState() == EntityState.None)
                    {
                        detail.SetState(EntityState.Deleted);
                    }
                });
                Consume.consume.Consume_Detail.ForEach((detail) =>
                {
                    //用了会员卡，要还卡里的钱和服务次数给客户
                    if (Consume.card != null && detail.mode_cd == "2")
                    {
                        var cardService = Consume.cardServices.FirstOrDefault(p => p.pro_id == detail.pro_id);
                        if (cardService != null)
                        {
                            if (cardService.consume_cnt.HasValue && cardService.surplus_cnt.HasValue)
                                cardService.surplus_cnt = cardService.surplus_cnt + (int)detail.number;
                        }
                        //被减去金额重新加回去，旧金额参数要初始化
                        Consume.card.surplus_bal = Consume.card.surplus_bal + detail.card_amt;
                        Consume.card.consume_amt = Consume.card.consume_amt - detail.card_amt;
                    }
                });
                Consume.consume.Consume_Detail.Clear();
                BindOrderDetail(grid);
            }
            else if (e.Parameters == "Reload")
            {
                BindOrderDetail(grid);
            }
            else if (e.Parameters == "Change_cust")
            {
                Consume.consume.Consume_Detail.ForEach((detail) =>
                {
                    //结算方式

                    object car_id = glp_cust.GridView.GetRowValues(glp_cust.GridView.FocusedRowIndex, "car_id");

                    detail.car_id = car_id;
                    object card_id = glp_cust.GridView.GetRowValues(glp_cust.GridView.FocusedRowIndex, "card_id");
                    if (card_id != null && !string.IsNullOrEmpty(card_id.ToString()))
                    {
                        Consume.card = _card.GetItem(Guid.Parse(card_id.ToString()));
                        Consume.cardSaleTypes = _cardSaleType.GetList(new QueryInfo<T_Vip_CardType_SaleType>().SetQuery("cardtype_id", Consume.card.cardtype_id)).ToList();
                        Consume.cardType_Services = _cardTypeService.GetList(new QueryInfo<T_Vip_CardType_Service>().SetQuery("cardtype_id", Consume.card.cardtype_id).SetQuery("is_free", false).SetQuery("is_order_detail", false)).ToList();
                        Consume.cardServices = _cardService.GetList(new QueryInfo<T_Vip_CardServices_Rela>().SetQuery("card_id", Consume.card.id).SetQuery("is_free", true)).ToList();
                        Consume.readCard = true;

                        detail.mode_cd = "2";
                        detail.mode_cd_name = bllBank.GetAccount(detail.mode_cd);
                    }
                    else
                    {
                        Consume.card = null;
                        Consume.cardSaleTypes = new List<T_Vip_CardType_SaleType>();
                        Consume.cardServices = new List<T_Vip_CardServices_Rela>();
                        detail.mode_cd = "1";
                        detail.rate = 10;
                        detail.cash_coupon = 0;
                        detail.mode_cd_name = bllBank.GetAccount(detail.mode_cd);
                    }
                    detail.account_det = DetailAmt(detail);
                });

                object cust_id = glp_cust.GridView.GetRowValues(glp_cust.GridView.FocusedRowIndex, "id");
                if (cust_id != null)
                {
                    Consume.consume.cust_id = Guid.Parse(cust_id.ToString());
                }

                BindOrderDetail(grid);
            }
            else
            {

                #region  选中添加
                if (parameters[0].IndexOf("AddPro|") != -1)
                {
                    //string ids = (parameters[0].IndexOf("AddPro|,") == -1) ? "" : parameters[0].Substring(parameters[0].IndexOf("AddPro|,"));
                    if (parameters.Length > 1)
                    {

                        for (int i = 1; i < parameters.Length; i += 3)
                        {
                            try
                            {
                                string str_proID = parameters[i].IsNullOrEmpty() ? Guid.Empty.ToString() : parameters[i];
                                string str_stockID = parameters[i + 1].IsNullOrEmpty() ? Guid.Empty.ToString() : parameters[i + 1];
                                str_stockID = str_stockID == "" ? Guid.Empty.ToString() : str_stockID;
                                Guid proID = new Guid(str_proID);
                                Guid stockID = new Guid(str_stockID);

                                LoadOrder(proID, stockID, Convert.ToDecimal(parameters[i + 2] == "" ? "0.0" : parameters[i + 2]));
                            }
                            catch (Exception ex)
                            { }
                        }
                    }
                }
                #endregion
                else
                {
                    Guid temp;
                    if (Guid.TryParse(parameters[0], out temp))
                    {
                        object o = gvw_Product.GetRowValuesByKeyValue(temp, new string[] { "id", "stock_id" });
                        if (o != null)
                        {
                            try
                            {
                                object[] row = (object[])o;
                                string str_proID = row[0] == null ? Guid.Empty.ToString() : row[0].ToString();
                                string str_stockID = row[1] == null ? Guid.Empty.ToString() : row[1].ToString();
                                str_stockID = str_stockID == "" ? Guid.Empty.ToString() : str_stockID;
                                Guid proID = new Guid(str_proID);
                                Guid stockID = new Guid(str_stockID);
                                LoadOrder(proID, stockID, Convert.ToDecimal(parameters.Length == 1 || parameters[1] == "" ? "0.0" : parameters[1]));
                            }
                            catch (Exception ex)
                            { }

                        }
                        else
                        {
                            Guid stockID = Guid.Empty;
                            LoadOrder(temp, stockID, Convert.ToDecimal("0.0"));

                        }
                    }
                    else
                    {

                        List<object> table = gvw_Product.GetSelectedFieldValues(new string[] { "id", "stock_id" });
                        if (table.Count == 0) return;
                        foreach (object[] row in table)
                        {
                            string str_proID = row[0] == null ? Guid.Empty.ToString() : row[0].ToString();
                            string str_stockID = row[1] == null ? Guid.Empty.ToString() : row[1].ToString();
                            str_stockID = str_stockID == "" ? Guid.Empty.ToString() : str_stockID;
                            Guid proID = new Guid(str_proID);
                            Guid stockID = new Guid(str_stockID);

                            LoadOrder(proID, stockID, Convert.ToDecimal(parameters.Length == 1 || parameters[1] == "" ? "0.0" : parameters[1]));
                        }

                    }

                }

                BindOrderDetail(gvw_OrderDetail);
                //Guid proID = new Guid(parameters.Split(',')[1]);
                //LoadOrder(proID);
                //BindOrderDetail(grid);

                gvw_OrderDetail.JSProperties["cp_add"] = "1";
                gvw_Product.Selection.UnselectAll();
            }
        }
        #endregion
        #region 订单明细金额、会员卡、数量等计算方法
        private decimal DetailAmt(dynamic detail)
        {
            return DetailAmt(detail, false);
        }
        private decimal DetailAmt(dynamic detail, bool isPause)
        {
            decimal account_det = 0;
            //原来的数量
            int o_number = (int)detail.o_number;
            //原来的实际支付金额(会员卡：包括已付的卡金额或卡金额+实收金额)
            //有卡消费了修改为现金或挂账或免单要处理的业务
            if (detail.mode_cd == "3" || detail.mode_cd == "4")
            {
                detail.card_amt = 0;
                detail.cope_amt = CountAmt((decimal)detail.unit_amt, (decimal)detail.number, (decimal)detail.cash_coupon, (decimal)detail.rate);
                if (detail.mode_cd == "3")
                    account_det = detail.cope_amt; //http://www.easybug.net/Bug/HandleBug/303890
                    //account_det = 0;
                return account_det;
            }
            else if ((detail.mode_cd == "1" || isPause) && Consume.card != null)
            {
                if (Consume.card != null)
                {
                    //查询购买商品是否在套餐中
                    var cardService = Consume.cardServices.FirstOrDefault(p => p.pro_id == detail.pro_id);
                    if (cardService != null)
                    {
                        //被减去的数量重新加回去，旧数量要初始化
                        if (cardService.consume_cnt.HasValue && cardService.surplus_cnt.HasValue)
                        {
                            cardService.surplus_cnt = cardService.surplus_cnt + o_number;
                            detail.o_number = 0;
                            cardService.SetState(EntityState.Modified);
                            if (detail.rate > cardService.cnt_rate) detail.rate = cardService.cnt_rate;
                        }
                        else
                            account_det = RateGoods(detail);
                    }
                    else
                        account_det = RateGoods(detail);
                    //http://www.easybug.net/Bug/HandleBug/295169
                    ////被减去金额重新加回去，旧金额参数要初始化
                    //Consume.card.surplus_bal = Consume.card.surplus_bal + detail.card_amt;
                    Consume.card.consume_amt -= detail.card_amt;
                    //卡结金额设0
                    detail.card_amt = 0;
                    if (detail.mode_cd == "1")
                        account_det = CountAmt((decimal)detail.unit_amt, (decimal)detail.number, (decimal)detail.cash_coupon, (decimal)detail.rate);
                }
                detail.cope_amt = account_det;
                return account_det;
            }
            else if (Consume.card != null && detail.mode_cd == "2")
            {
                //查询购买商品是否在套餐中
                var cardService = Consume.cardServices.FirstOrDefault(p => p.pro_id == detail.pro_id);
                if (cardService != null)
                {
                    //减去服务项目商品的次数、或次数用完转成货币支付，
                    //在下步判断如果会员卡余额够，走卡支付交程，否则以卡金额+现金支付
                    if (cardService.consume_cnt.HasValue && cardService.surplus_cnt.HasValue)
                    {
                        if (cardService.surplus_cnt.Value + o_number > 0)
                        {
                            cardService.surplus_cnt = cardService.surplus_cnt + o_number - (int)detail.number;
                            cardService.SetState(EntityState.Modified);
                            if (detail.rate > cardService.cnt_rate) detail.rate = cardService.cnt_rate;
                        }
                        else
                            account_det = RateGoods(detail);
                    }
                    else
                        account_det = RateGoods(detail);
                }
                else
                    account_det = RateGoods(detail);
            }
            else
            {
                //卡结金额设0
                detail.card_amt = 0;
                account_det = CountAmt((decimal)detail.unit_amt, (decimal)detail.number, (decimal)detail.cash_coupon, (decimal)detail.rate);
            }
            //减去会员卡的金额,如果卡金额够减则按实际金额减去，否则减去金额的同时还要付差额
            if (account_det >= 0 && Consume.card != null && detail.mode_cd == "2")
            {
                //卡余额+上次卡付金额>=本次实收
                //卡余额+上次卡付金额====相当于把卡中上次扣掉金额还原
                //if (Consume.card.surplus_bal + detail.card_amt >= account_det)
                //{
                //卡里有钱给你扣
                //卡余额 = 卡余额 + 上次卡付金额 - 当前卡付金额
                Consume.card.surplus_bal = Consume.card.surplus_bal + detail.card_amt - account_det;
                //累计消费余额 = 累计消费余额 - 上次卡付金额 + 当前卡付金额
                Consume.card.consume_amt = Consume.card.consume_amt - detail.card_amt + account_det;
                detail.card_amt = account_det;
                account_det = 0;
                //}
                //else
                //{
                //detail.card_amt = account_det;
                //account_det = 0;
                //卡里没钱或钱不够
                //account_det = account_det - (Consume.card.surplus_bal + detail.card_amt);
                //会员卡里没金额变现金
                //if (Consume.card.surplus_bal + detail.card_amt == 0)
                //{
                //    detail.mode_cd = "1";
                //    detail.mode_cd_name =bllBank.GetAccount(detail.mode_cd);
                //}
                //detail.card_amt = Consume.card.surplus_bal + detail.card_amt;
                //Consume.card.surplus_bal = 0;
                //}
                //Consume.card.consume_amt += detail.card_amt;
            }
            detail.cope_amt = account_det + detail.card_amt;
            //本次消费的实际数量赋值
            detail.o_number = detail.number;
            return account_det;
        }
        /// <summary>
        /// 特定商品的折扣，以最低折扣计算
        /// </summary>
        /// <param name="detail"></param>
        /// <returns></returns>
        private decimal RateGoods(dynamic detail)
        {
            //查询购买商品是否在商品销售类型折扣明细中
            var cardSaleType = Consume.cardSaleTypes.FirstOrDefault(p => p.service_as == detail.consume_type_cd);
            //特定商品的折扣，以最低折扣计算
            decimal rate = 10;
            var cardType_Service = Consume.cardType_Services.FirstOrDefault(p => p.pro_id == detail.pro_id);
            if (cardType_Service != null && cardSaleType != null)
            {
                if (cardSaleType.rate < cardType_Service.cnt_rate)
                    rate = cardSaleType.rate;
                else
                    rate = cardType_Service.cnt_rate;
            }
            else if (cardType_Service != null && cardSaleType == null)
                rate = cardType_Service.cnt_rate;
            else if (cardType_Service == null && cardSaleType != null)
                rate = cardSaleType.rate;
            if (detail.rate > rate) detail.rate = rate;
            return CountAmt((decimal)detail.unit_amt, (decimal)detail.number, (decimal)detail.cash_coupon, (decimal)detail.rate);
        }
        private decimal CountAmt(decimal unit_amt, decimal number, decimal cash_coupon, decimal rate)
        {
            if (cash_coupon > unit_amt * number) return 0;
            return (unit_amt * number * rate * (decimal)0.1) - cash_coupon;
        }

        public void LoadOrder(Guid proID, Guid stock_id, decimal amt)
        {
            //实体数据转到动态数据
            dynamic detail = _dao.ModeltoDynamic<T_Sal_Consume_Detail>(new T_Sal_Consume_Detail());
            detail.state = "add";//删除要使用
            T_Prd_Info prd_Info = BLL.T_Prd_Info.provide.GetModel(proID);
            detail.id = Guid.NewGuid();
            detail.order_id = Consume.consume.id;
            detail.pro_id = prd_Info.id;
            detail.unit_amt = prd_Info.sales_amt ?? 0;
            if (stock_id != Guid.Empty)
            {
                detail.stock_id = stock_id;
            }
            else
            {
                if (null != prd_Info)
                {
                    T_Prd_Info model = BLL.T_Prd_Info.provide.GetModelList(string.Format("name='{0}' and is_service=1", prd_Info.name)).FirstOrDefault();
                    if (null != model)
                    {
                        Model.T_Stock_Info stockModel =
                            new BLL.T_Stock_Info().GetModelByProductIDAndShopID(model.id.ToString(),
                            UIHelpers.UserProvide.GetCurrentShopGuid().Value.ToString());
                        detail.stock_id = null != stockModel ? stockModel.id : Guid.Empty;
                    }
                }
            }
            //结算方式、消费类型
            detail.consume_type_cd = prd_Info.service_as;
            detail.number = 1;

            if (!string.IsNullOrEmpty(hfd_car_id.Value))
            {
                detail.car_id = new Guid(hfd_car_id.Value.ToString());
            }
            if (!string.IsNullOrEmpty(hfd_cust_id.Value))
            {
                Consume.consume.cust_id = new Guid(hfd_cust_id.Value.ToString());
            }

            //结算方式
            if (!string.IsNullOrEmpty(hfd_card_id.Value))
            {
                if (!Consume.readCard)
                {
                    Consume.card = _card.GetItem(Guid.Parse(hfd_card_id.Value));
                    Consume.cardSaleTypes = _cardSaleType.GetList(new QueryInfo<T_Vip_CardType_SaleType>().SetQuery("cardtype_id", Consume.card.cardtype_id)).ToList();
                    Consume.cardType_Services = _cardTypeService.GetList(new QueryInfo<T_Vip_CardType_Service>().SetQuery("cardtype_id", Consume.card.cardtype_id).SetQuery("is_free", false).SetQuery("is_order_detail", false)).ToList();
                    Consume.cardServices = _cardService.GetList(new QueryInfo<T_Vip_CardServices_Rela>().SetQuery("card_id", Consume.card.id).SetQuery("is_free", true)).ToList();
                    Consume.readCard = true;
                }
                int cardNum = 0;
                foreach (T_Vip_CardServices_Rela rela in Consume.cardServices)
                {
                    if (rela.pro_id == proID)
                    {
                        cardNum++;
                    }
                }

                if (cardNum > 0)
                    detail.mode_cd = "2"; //会员卡
                else if (amt > 0)
                    detail.mode_cd = "2"; //会员卡
                else
                    detail.mode_cd = "1"; //现金
            }
            else detail.mode_cd = "1";


            //合计
            detail.sum_bal = detail.unit_amt * detail.number;
            //折扣
            detail.rate = 10;
            //初始化原来数量和金额
            detail.o_number = 0;
            detail.account_det = DetailAmt(detail);
            detail.add_date = detail.update_date = DateTime.Now;
            detail.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            #region 如果订单上有结算方式
            //if (string.IsNullOrEmpty(Consume.consume.mode_cd)) Consume.consume.mode_cd = mode_cd.Value.ToString();
            //detail.mode_cd = Consume.consume.mode_cd;
            #endregion
            //增加UI需要字段，取数据库数据也要生成这几个字段
            detail.pro_name = prd_Info.name;
            detail.format = string.IsNullOrEmpty(prd_Info.format) ? string.Empty : prd_Info.format;
            //detail.car_no = cbb_car_area.Text + txt_car_no.Text;
            detail.Consume_Crew = new List<dynamic>();
            detail.Crews = string.Empty;
            detail.Crews_Name = string.Empty;
            detail.CrewsSale = string.Empty;
            detail.CrewsSale_Name = string.Empty;
            detail.mode_cd_name = bllBank.GetAccount(detail.mode_cd);
            //detail.consume_type_cd_name = service_as_name;

            Consume.consume.Consume_Detail.Add(detail);
            //ApplyOrderInContainer(tb_order);

            Consume = Consume;
        }


        public void LoadOrder(Guid proID)
        {
            //实体数据转到动态数据
            dynamic detail = _dao.ModeltoDynamic<T_Sal_Consume_Detail>(new T_Sal_Consume_Detail());
            detail.state = "add";//删除要使用
            T_Prd_Info prd_Info = BLL.T_Prd_Info.provide.GetModel(proID);
            detail.id = Guid.NewGuid();
            detail.order_id = Consume.consume.id;
            detail.pro_id = prd_Info.id;
            detail.unit_amt = prd_Info.sales_amt ?? 0;
            //结算方式、消费类型
            detail.consume_type_cd = prd_Info.service_as;
            detail.number = 1;
            if (!string.IsNullOrEmpty(hfd_car_id.Value))
                detail.car_id = new Guid(hfd_car_id.Value.ToString());
            //结算方式
            if (!string.IsNullOrEmpty(hfd_card_id.Value))
            {
                if (!Consume.readCard)
                {
                    Consume.card = _card.GetItem(Guid.Parse(hfd_card_id.Value));
                    Consume.cardSaleTypes = _cardSaleType.GetList(new QueryInfo<T_Vip_CardType_SaleType>().SetQuery("cardtype_id", Consume.card.cardtype_id)).ToList();
                    Consume.cardType_Services = _cardTypeService.GetList(new QueryInfo<T_Vip_CardType_Service>().SetQuery("cardtype_id", Consume.card.cardtype_id).SetQuery("is_free", false).SetQuery("is_order_detail", false)).ToList();
                    Consume.cardServices = _cardService.GetList(new QueryInfo<T_Vip_CardServices_Rela>().SetQuery("card_id", Consume.card.id).SetQuery("is_free", true)).ToList();
                    Consume.readCard = true;
                }
                detail.mode_cd = "2";
            }
            else detail.mode_cd = "1";
            //合计
            detail.sum_bal = detail.unit_amt * detail.number;
            //折扣
            detail.rate = 10;
            //初始化原来数量和金额
            detail.o_number = 0;
            detail.account_det = DetailAmt(detail);
            detail.add_date = detail.update_date = DateTime.Now;
            detail.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            #region 如果订单上有结算方式
            //if (string.IsNullOrEmpty(Consume.consume.mode_cd)) Consume.consume.mode_cd = mode_cd.Value.ToString();
            //detail.mode_cd = Consume.consume.mode_cd;
            #endregion
            //增加UI需要字段，取数据库数据也要生成这几个字段
            detail.pro_name = prd_Info.name;
            detail.format = string.IsNullOrEmpty(prd_Info.format) ? string.Empty : prd_Info.format;
            //detail.car_no = cbb_car_area.Text + txt_car_no.Text;
            detail.Consume_Crew = new List<dynamic>();
            detail.Crews = string.Empty;
            detail.Crews_Name = string.Empty;
            detail.CrewsSale = string.Empty;
            detail.CrewsSale_Name = string.Empty;
            detail.mode_cd_name = bllBank.GetAccount(detail.mode_cd);
            //detail.consume_type_cd_name = service_as_name;

            Consume.consume.Consume_Detail.Add(detail);
            //ApplyOrderInContainer(tb_or
        }
        #endregion
        #region 选择结算方式后重新计算
        protected void cbk_total_Callback(object sender, DevExpress.Web.ASPxCallback.CallbackEventArgs e)
        {
            Consume.consume.amt_mode_cd = e.Parameter;
            if (Consume.consume.amt_mode_cd == "3")
            {
                string acct_car_no = cbb_car_area.Text + txt_car_no.Text;
                //var acct_setting = _acct.GetItem(acct_car_no,UIHelpers.UserProvide.GetCurrentShopId());
                var acct_setting = _acct.GetItem(acct_car_no);
                if (acct_setting == null)
                {
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "该客户不属于默认门店可挂账用户" });
                    return;
                }
            }
            foreach (dynamic model in Consume.consume.Consume_Detail)
            {
                model.mode_cd = e.Parameter;
                model.mode_cd_name = cbb_mode_cd.Text;
                model.account_det = DetailAmt(model);
            }
            e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = true, msg = "重新计算完成" });
        }
        #endregion
        #region 提交订单
        protected void cbk_Buy_Callback(object sender, DevExpress.Web.ASPxCallback.CallbackEventArgs e)
        {

            string putStatus = e.Parameter;
            Consume.consume.paid_in = ConvertHelper.ObjectToDecimal(sdt_paid_in.Value);//收银
            Consume.consume.give_change = ConvertHelper.ObjectToDecimal(sdt_give_change.Value);//找钱
            decimal total_amt = ConvertHelper.ObjectToDecimal(card_amt.Value);//卡结金额
            decimal surplus_bal = ConvertHelper.ObjectToDecimal(sdt_surplus_bal1.Value);//卡余额
            decimal gz_total_amt = ConvertHelper.ObjectToDecimal(sdt_total_amt.Value);//应收用于挂账
            Consume.consume.remarks = txt_remarks.Text;
            Consume.consume.amt_mode_cd = cbb_mode_cd.KeyValue.ToString();
            if (!string.IsNullOrEmpty(hfd_card_id.Value))
            {
                Consume.consume.card_id = Guid.Parse(hfd_card_id.Value);
                Consume.consume.card_no = hfd_card_no.Value;
            }
            Consume.consume.acct_no = txt_acct_no.Text;
            var consume = Consume;
            #region 验证是否通过消费
            if (Consume.consume.Consume_Detail.Count == 0)
            {
                e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "没有订单明细，无法提交" });
                return;
            }
            string mode_cd = "1";
            foreach (dynamic dynDetail in Consume.consume.Consume_Detail)
            {
                if (dynDetail.mode_cd == "2")
                {
                    if (!Consume.consume.card_id.HasValue)
                    {
                        e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "该客户不是会员,消费类型不能是会员卡" });
                        return;
                    }
                }
                else if (dynDetail.mode_cd == "3")
                {
                    mode_cd = "3";
                }
            }
            if (putStatus == "sale")
            {
                if (Consume.consume.account > Consume.consume.paid_in && Consume.consume.amt_mode_cd != "3")
                {
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "收银金额不正确" });
                    return;
                }
            }
            if (Consume.consume.amt_mode_cd == "2" && putStatus == "sale")
            {
                if (!Consume.card.card_status)
                {
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "会员卡已失效，无法使用" });
                    return;
                }
                else
                {
                    //if (!Consume.card.start_date.HasValue || !Consume.card.end_date.HasValue)
                    //{
                    //    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "会员卡套餐已到期，无法使用" });
                    //    return;
                    //}
                    //if (Consume.card.start_date.HasValue && Consume.card.end_date.HasValue)
                    //{
                    //    if (DateTime.Compare(DateTime.Now, Consume.card.end_date.Value) > 0)
                    //    {
                    //        e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "会员卡套餐已到期，无法使用" });
                    //        return;
                    //    }
                    //}
                }

                if (total_amt > surplus_bal || gz_total_amt > surplus_bal)  //wgy 挂帐金额比卡里钱多，不能支付
                {
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "会员卡余额不足，请充值" });
                    return;
                }
                //余额减卡结金额不等于当前余额的修改当前余额
                if ((surplus_bal - total_amt) != Consume.card.surplus_bal)
                {
                    Consume.card.surplus_bal = (surplus_bal - total_amt);
                }
                if (string.IsNullOrEmpty(txt_card_pwd.Text))
                {
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "会员卡结算需要填写卡密码" });
                    return;
                }
                else
                {
                    if (Consume.card.card_pwd != txt_card_pwd.Text)
                    {
                        e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "卡密码不正确，请重新输入" });
                        return;
                    }
                }
            }
            else
            {
                if (Consume.card != null && Consume.card.surplus_bal < surplus_bal)
                    Consume.card.surplus_bal = surplus_bal;
            }
            T_Vip_Acct acct_setting = null;
            if (Consume.consume.amt_mode_cd == "3")
            {
                string acct_car_no = cbb_car_area.Text + txt_car_no.Text;
                // acct_setting = _acct.GetItem(acct_car_no,UIHelpers.UserProvide.GetCurrentShopId());
                acct_setting = _acct.GetItemByAcct(acct_car_no, txt_cust_name.Text);
                if (acct_setting == null)
                {
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "该客户不属于可挂账用户" });
                    return;
                }
                else
                {
                    if ((gz_total_amt + acct_setting.acct_bal) > acct_setting.acct_upper)
                    {
                        e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "您已超过挂账信用额" });
                        return;
                    }
                }
            }
            #endregion
            #region 非客户、车辆未登记 先做增加记录
            T_Vip_Customer customer = null;
            T_Vip_Car car = null;
            if (string.IsNullOrEmpty(hfd_cust_id.Value))
            {
                customer = new T_Vip_Customer(); customer.SetState(EntityState.Added);
                Consume.consume.cust_id = customer.id = Guid.NewGuid();//给订单的客户ID赋值
                customer.cust_no = UIHelper.InitCustomerCode();
                customer.cust_name = txt_cust_name.Text;
                customer.telphone = txt_telphone.Text;
                customer.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
                customer.add_date = customer.update_date = DateTime.Now;
                customer.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
            }
            else
            {
                Consume.consume.cust_id = Guid.Parse(hfd_cust_id.Value);
                customer = _cust.GetItem(Consume.consume.cust_id);
                customer.SetState(EntityState.Modified);
                customer.cust_name = txt_cust_name.Text;
                customer.telphone = txt_telphone.Text;
                customer.update_date = DateTime.Now;
            }
            Consume.customer = customer;
            string car_no = cbb_car_area.Text + txt_car_no.Text;
            if (string.IsNullOrEmpty(hfd_car_id.Value) && !string.IsNullOrEmpty(car_no))
            {
                car = new T_Vip_Car(); car.SetState(EntityState.Added);
                car.id = Guid.NewGuid();
                Consume.consume.Consume_Detail.ForEach((a) => { a.car_id = car.id; });//给订单明细的车辆ID赋值
                car.car_no = car_no;
                car.cust_id = Consume.consume.cust_id;
                if (cbb_brand.Value != null)
                    car.brand = cbb_brand.Value.ToString();
                if (cbb_car_type.Value != null)
                    car.car_type = cbb_car_type.Value.ToString();
                if (cbb_car_sales.Value != null)
                    car.car_sales = ConvertHelper.ObjectToInt(cbb_car_sales.Value.ToString());
                if (sdt_mileage.Value != null)
                {
                    car.km_num = sdt_mileage.Number;
                    if (!car.mileage.HasValue) car.mileage = sdt_mileage.Number;
                }
                car.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
                car.add_date = car.update_date = DateTime.Now;
                car.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                Consume.cars.Add(car);
            }
            else
            {
                car = _car.GetItem(Guid.Parse(hfd_car_id.Value.ToString()));
                car.SetState(EntityState.Modified);
                if (cbb_brand.Value != null)
                    car.brand = cbb_brand.Value.ToString();
                if (cbb_car_type.Value != null)
                    car.car_type = cbb_car_type.Value.ToString();
                if (cbb_car_sales.Value != null)
                    car.car_sales = ConvertHelper.ObjectToInt(cbb_car_sales.Value.ToString());
                if (sdt_mileage.Value != null)
                {
                    car.km_num = sdt_mileage.Number;
                    if (!car.mileage.HasValue) car.mileage = sdt_mileage.Number;
                }
                Consume.cars.Add(car);
            }
            #endregion
            #region 存储消费三表张
            decimal cp_sales = 0, cp_account = 0, cp_cash = 0, cp_card = 0;
            foreach (dynamic dynDetail in Consume.consume.Consume_Detail)
            {
                //新增、修改明细
                T_Sal_Consume_Detail detail = new T_Sal_Consume_Detail();
                if (Consume.consume.status_cd == "5" || dynDetail.state == "add") detail.SetState(EntityState.Added);
                else detail.SetState(EntityState.Modified);
                if (dynDetail.car_id == null)
                    dynDetail.car_id = new Guid(hfd_car_id.Value.ToString());
                //挂单如果是会员的不能扣会员卡的钱和套餐次数,从挂单再结算要扣钱扣次数
                if (putStatus == "pause")
                {
                    if (Consume.consume.status_cd == "2")
                        dynDetail.o_number = 0;
                    detail.account_det = DetailAmt(dynDetail, true);
                }
                else if (putStatus == "sale" && id.HasValue)
                {
                    if (Consume.consume.status_cd == "2")
                        dynDetail.o_number = 0;
                    detail.account_det = DetailAmt(dynDetail);
                }

                UIHelpers.UpdateDynamic.ToUpdateModel<T_Sal_Consume_Detail>(detail, dynDetail);
                if (ConvertHelper.ObjectToInt(detail.mode_cd) == 0)
                {
                    detail.mode_cd = bllBank.GetList().FirstOrDefault(p => p.Account == detail.mode_cd).ID;
                }
                detail.update_date = DateTime.Now;
                #region 新增订单明细的车辆，现在没有用，扩展功能
                //string car_no = cbb_car_area.Text + txt_car_no.Text;
                //if (!string.IsNullOrEmpty(car_no) && string.IsNullOrEmpty(hfd_car_id.Value) && Consume.cars.FirstOrDefault(p => p.car_no == car_no) == null)
                //{
                //    var car_item = _car.GetList(new QueryInfo<T_Vip_Car>().SetQuery("car_no", car_no));
                //    if (car_item.Count > 0)
                //        detail.car_id = car_item[0].id;
                //    else
                //    {
                //        var car1 = new T_Vip_Car(); car1.SetState(EntityState.Added);
                //        car1.id = Guid.NewGuid();
                //        detail.car_id = car1.id;//给订单明细的车辆ID赋值
                //        car1.car_no = car_no;
                //        car1.cust_id = Consume.consume.cust_id;
                //        car1.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
                //        car1.add_date = car1.update_date = DateTime.Now;
                //        car1.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                //        Consume.cars.Add(car1);
                //    }
                //}
                #endregion
                var pro = BLL.T_Prd_Info.provide.GetModel(detail.pro_id.Value);
                if (pro.is_service)
                    detail.cost = pro.hist_bid_amt ?? 0 * detail.number;
                else
                    detail.cost = BLL.T_Stock_Info.provide.GetProductStockBitAmt(detail.pro_id.ToString(), Consume.consume.shop_id.ToString()) * detail.number;
                cp_sales += detail.sum_bal;
                cp_account += detail.account_det;
                cp_cash += detail.account_det;
                cp_card += detail.card_amt;
                Consume.details.Add(detail);
                #region 销售、施工、提成
                if (putStatus == "sale" && (Consume.consume.amt_mode_cd == "1" || Consume.consume.amt_mode_cd == "2"))
                {
                    //判断是否有提成
                    decimal account_det = (decimal)detail.account_det + (decimal)detail.card_amt;
                    var crew_ids = string.IsNullOrEmpty(dynDetail.Crews) ? new List<Guid>() : ((string)dynDetail.Crews).Split(',').Select(p => Guid.Parse(p)).ToList();
                    var crewSale_ids = string.IsNullOrEmpty(dynDetail.CrewsSale) ? new List<Guid>() : ((string)dynDetail.CrewsSale).Split(',').Select(p => Guid.Parse(p)).ToList();
                    //if (crew_ids.Count > 0 && crewSale_ids.Count > 0) account_det = account_det / 2;
                    decimal crew_account_det = 0;
                    decimal costunit = detail.cost * detail.number;
                    int empCount = crew_ids.Count + crewSale_ids.Count;
                    if (empCount > 0)
                    {
                        crew_account_det = account_det / empCount;//分摊业绩
                        costunit = costunit / empCount;
                    }
                    //清空原来的数据
                    var dt = DateTime.Now;
                    foreach (Guid crew in crew_ids)
                    {
                        T_Sal_Consume_Crew crewItem = new T_Sal_Consume_Crew(); crewItem.SetState(EntityState.Added);
                        crewItem.id = Guid.NewGuid();
                        crewItem.consume_id = detail.id;
                        crewItem.emp_id = crew;
                        crewItem.royalty_type = 2;
                        RoyaltyHelper.Royalty(crew, detail, account_det, crew_ids.Count, crewItem);
                        crewItem.consume_amt = account_det/crew_ids.Count;
                        crewItem.gross_amt = crew_account_det - costunit;
                        crewItem.add_date = crewItem.update_date = dt;
                        crewItem.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                        //crewItem.royalty_amt = new RoyaltyHelper().Royalty(crewItem, empCount);
                        Consume.crews.Add(crewItem);
                    }
                    foreach (Guid crew in crewSale_ids)
                    {
                        T_Sal_Consume_Crew crewItem = new T_Sal_Consume_Crew(); crewItem.SetState(EntityState.Added);
                        crewItem.id = Guid.NewGuid();
                        crewItem.consume_id = detail.id;
                        crewItem.emp_id = crew;
                        crewItem.royalty_type = 1;
                        RoyaltyHelper.Royalty(crew, detail, account_det, crewSale_ids.Count, crewItem);
                        crewItem.consume_amt = account_det / crewSale_ids.Count;
                        crewItem.gross_amt = crew_account_det - costunit;
                        crewItem.add_date = crewItem.update_date = dt;
                        crewItem.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                        Consume.crews.Add(crewItem);
                    }
                }
                else
                {
                    //判断是否有提成
                    var crew_ids = string.IsNullOrEmpty(dynDetail.Crews) ? new List<Guid>() : ((string)dynDetail.Crews).Split(',').Select(p => Guid.Parse(p)).ToList();
                    var crewSale_ids = string.IsNullOrEmpty(dynDetail.CrewsSale) ? new List<Guid>() : ((string)dynDetail.CrewsSale).Split(',').Select(p => Guid.Parse(p)).ToList();
                    //清空原来的数据
                    var dt = DateTime.Now;
                    foreach (Guid crew in crew_ids)
                    {
                        T_Sal_Consume_Crew crewItem = new T_Sal_Consume_Crew(); crewItem.SetState(EntityState.Added);
                        crewItem.id = Guid.NewGuid();
                        crewItem.consume_id = detail.id;
                        crewItem.emp_id = crew;
                        crewItem.royalty_type = 2;
                        RoyaltyHelper.Royalty(crew, detail, 0, 0, crewItem);
                        crewItem.consume_amt = 0;
                        crewItem.gross_amt = 0;
                        crewItem.add_date = crewItem.update_date = dt;
                        crewItem.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                        Consume.crews.Add(crewItem);
                    }
                    foreach (Guid crew in crewSale_ids)
                    {
                        T_Sal_Consume_Crew crewItem = new T_Sal_Consume_Crew(); crewItem.SetState(EntityState.Added);
                        crewItem.id = Guid.NewGuid();
                        crewItem.consume_id = detail.id;
                        crewItem.emp_id = crew;
                        crewItem.royalty_type = 1;
                        RoyaltyHelper.Royalty(crew, detail, 0, 0, crewItem);
                        crewItem.consume_amt = 0;
                        crewItem.gross_amt = 0;
                        crewItem.add_date = crewItem.update_date = dt;
                        crewItem.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                        Consume.crews.Add(crewItem);
                    }
                }
                #endregion
            }
            #region 会员卡内金额不够，就将收银金额充值到卡中
            if (Consume.consume.amt_mode_cd == "2" && consume.card != null && cp_account > 0)
            {
                Sal_Consume consume1 = new Sal_Consume();
                consume1.consume = new T_Sal_Consume();
                consume1.consume.id = Guid.NewGuid();
                consume1.consume.account = consume1.consume.cash = consume1.consume.sales_amt = cp_account;
                consume1.consume.acct_no = DateTime.Now.ToString("yyyyMMddHHmmssffffff");
                consume1.consume.paid_in = Consume.consume.paid_in;
                consume1.consume.give_change = Consume.consume.give_change;
                consume1.consume.amt_mode_cd = "1";
                consume1.consume.card_bal = 0;
                consume1.consume.card_id = Consume.card.id;
                consume1.consume.card_no = Consume.card.card_no;
                consume1.consume.cust_id = Consume.customer.id;
                consume1.consume.shop_id = UIHelpers.UserProvide.GetCurrentShopGuid();
                consume1.consume.status_cd = "1";
                consume1.consume.order_type = 4;
                consume1.consume.add_date = consume1.consume.update_date = DateTime.Now;
                consume1.consume.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                consume1.consume.SetState(EntityState.Added);
                //var pro = BLL.T_Prd_Info.provide.GetModelList("service_as='charge'")[0];
                var consume_detail = new T_Sal_Consume_Detail();
                consume_detail.id = Guid.NewGuid();
                consume_detail.account_det = cp_account;
                consume_detail.add_date = DateTime.Now;
                consume_detail.car_id = new Guid(hfd_car_id.Value.ToString());
                consume_detail.card_amt = 0;
                consume_detail.cash_coupon = 0;
                //consume_detail.consume_type_cd = pro.service_as;
                consume_detail.consume_type_cd = "charge";

                consume_detail.cost = 0;
                consume_detail.mode_cd = "1";
                consume_detail.number = 1;
                consume_detail.order_id = consume1.consume.id;
                //consume_detail.pro_id = pro.id;
                //consume_detail.pro_name = pro.name;
                consume_detail.pro_id = null;
                consume_detail.pro_name = "充值";
                consume_detail.rate = 10;
                consume_detail.sum_bal = cp_account;
                consume_detail.cope_amt = cp_account;
                consume_detail.unit_amt = 0;
                consume_detail.update_date = DateTime.Now;
                consume_detail.update_opr = UIHelpers.UserProvide.GetCurrentUserName();
                consume_detail.SetState(EntityState.Added);
                consume1.details.Add(consume_detail);

                consume1.card_credit = new T_Vip_Card_Credit();
                consume1.card_credit.SetState(EntityState.Added);
                consume1.card_credit.id = Guid.NewGuid();
                consume1.card_credit.account_det = cp_account;
                consume1.card_credit.card_id = consume.card.id;
                consume1.card_credit.consume_amt = consume.card.consume_amt;
                consume1.card_credit.credit_amt = cp_account;
                consume1.card_credit.surplus_bal = consume.card.surplus_bal + cp_account;
                if (_consume.Batch(consume1) > 0)
                {
                    Consume.consume.paid_in = 0;
                    Consume.consume.give_change = 0;
                    cp_sales = 0;
                    cp_account = 0;
                    cp_cash = 0;
                    cp_card = 0;
                    Consume.details.ForEach((detail) =>
                    {
                        detail.update_date = DateTime.Now;
                        detail.card_amt = detail.cope_amt;
                        detail.account_det = 0;
                        cp_sales += detail.sum_bal;
                        cp_card += detail.card_amt;
                    });
                }
            }
            #endregion
            Consume.consume.account = cp_account;
            Consume.consume.sales_amt = cp_sales;
            Consume.consume.cash = cp_cash;
            Consume.consume.card_bal = cp_card;
            //http://www.easybug.net/Bug/HandleBug/300698
            decimal cp_real = 0;
            foreach (dynamic model in Consume.consume.Consume_Detail)
            {
               cp_real+= CountAmt((decimal)model.unit_amt, (decimal)model.number, (decimal)model.cash_coupon, (decimal)model.rate); //实收
            }
             
            if (Consume.consume.status_cd == "5") Consume.consume.SetState(EntityState.Added);
            else Consume.consume.SetState(EntityState.Modified);
            if (putStatus == "sale")
            {
                if (Consume.consume.amt_mode_cd == "3")
                {  //add
                    Consume.consume.status_cd = "3";

                    //acct_setting.owe_bal = acct_setting.owe_bal + cp_real; 
                    //acct_setting.acct_bal = acct_setting.acct_bal + cp_sales;
                    //_acct.Update(acct_setting);
                }
                else
                    Consume.consume.status_cd = "1";
            }
            else if (putStatus == "pause")
            {
                Consume.consume.status_cd = "2";
                //if (mode_cd == "3")
                //{
                //    string acct_car_no = cbb_car_area.Text + txt_car_no.Text;
                //    // acct_setting = _acct.GetItem(acct_car_no,UIHelpers.UserProvide.GetCurrentShopId());
                //    acct_setting = _acct.GetItem(acct_car_no);
                //    if (acct_setting != null)
                //    {
                //        acct_setting.owe_bal = acct_setting.owe_bal + cp_real;
                //        acct_setting.acct_bal = acct_setting.acct_bal + cp_sales;
                //        _acct.Update(acct_setting);
                //    }
                //}
            }
            Consume.consume.update_date = DateTime.Now;
            #endregion
            #region 积分
            if (consume.card != null && consume.consume.account > 0)
            {
                if (!consume.card.surplus_integral.HasValue) consume.card.surplus_integral = 0;
                consume.card.surplus_integral += (int)consume.consume.account;
                consume.card.SetState(EntityState.Modified);
            }
            #endregion
            //int result = 1;
            int result = _consume.Batch(consume);
            if (result > 0)
            {
                //Consume.consume.GetState() == EntityState.Added && http://easybug.net/Bug/HandleBug/298980
                if (Consume.consume.status_cd == "1" || Consume.consume.status_cd == "3")
                    Consume.details.ForEach((d) =>
                    {
                        if (d.stock_id != null && d.stock_id != Guid.Empty)
                        {
                            //BLL.T_Stock_Info.provide.DeductStockNumber(d.pro_id.ToString(), Consume.consume.shop_id.ToString(), d.number);
                            BLL.T_Stock_Info.provide.DeductSaleStockNumber(d.pro_id.ToString(), d.stock_id.ToString(), d.number);
                        }
                    });
                Session["custCars"] = null;

                //更新余额
                //IList<dynamic> lc = Session["custList_buy"] as IList<dynamic>;
                //if (null != lc)
                //{
                //    var p = lc.FirstOrDefault(c => c.card_no == Consume.card.card_no);
                //    p.surplus_bal = Consume.card.surplus_bal;
                //    Session["custList_buy"] = lc;
                //}

                CacheHelper.Current.Remove("order_" + UIHelpers.UserProvide.GetCurrentUserName() + "_" + (id ?? Guid.Empty));
                if (putStatus == "sale")
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = true, msg = "结算成功", close = id.HasValue, putStatus = putStatus, id = consume.consume.id });
                else if (putStatus == "pause")
                    e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = true, msg = "挂单成功", close = id.HasValue, putStatus = putStatus, id = consume.consume.id });
            }
            else e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = false, msg = "提交失败", close = id.HasValue, putStatus = putStatus, id = consume.consume.id });

            //如果类型为“sale”,并且结算成功，就发送短信（吴，2013-09-20）
            if ((e.Result.IndexOf("sale") >= 0) && (e.Result.IndexOf("结算成功") >= 0))
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
                        string mode_cd2 = ds_order.Tables[0].Rows[0]["mode_cd"].ToString();//结算方式（1：现金；2：会员卡）
                        if (mode_cd2 == "2")   //结算方式是会员卡类型的，才需要短信发送
                        {
                            string car_id = ds_order.Tables[0].Rows[0]["car_id"].ToString();  //对应T_Vip_Car表id
                            decimal price = 0;
                            for (int k = 0; k < ds_order.Tables[0].Rows.Count; k++)
                            {
                                price += Decimal.Parse(ds_order.Tables[0].Rows[k]["cope_amt"].ToString());
                            }
                            string sum_bal = price.ToString();//消费总金额
                            DateTime add_date = DateTime.Parse(ds_order.Tables[0].Rows[0]["add_date"].ToString());//消费时间
                            string time = add_date.Year.ToString() + "年" + add_date.Month.ToString() + "月" + add_date.Day.ToString() + "日" + add_date.Hour.ToString() + "时" + add_date.Minute.ToString() + "分";
                            DataSet ds_car = DbHelperSQL.Query("select * from T_Vip_Car where id='" + car_id + "'");
                            if (ds_car.Tables[0].Rows.Count > 0)
                            {
                                string cust_id = ds_car.Tables[0].Rows[0]["cust_id"].ToString(); //对应T_Vip_Customer表id
                                string shop_id = ds_car.Tables[0].Rows[0]["shop_id"].ToString(); //对应T_Cm_Shop表id
                                string card_id = ds_car.Tables[0].Rows[0]["card_id"].ToString(); //对应T_Vip_Card表id
                                string car_no1 = ds_car.Tables[0].Rows[0]["car_no"].ToString();   //车牌号码
                                DataSet ds_cust = DbHelperSQL.Query("select * from T_Vip_Customer where id='" + cust_id + "'");
                                if (ds_cust.Tables[0].Rows.Count > 0)
                                {
                                    //会员卡结算后的短信提醒
                                    bool ismember = bool.Parse(ds_cust.Tables[0].Rows[0]["is_member"].ToString()); //是否VIP（1：vip，进行短信发送；0或null：非VIP,不进行短信发送）
                                    DataSet ds_card = DbHelperSQL.Query("select * from T_Vip_Card where id='" + card_id + "'");
                                    DataSet ds_shop = DbHelperSQL.Query("select * from T_Cm_Shop where id='" + shop_id + "'");
                                    string tel = ds_cust.Tables[0].Rows[0]["telphone"].ToString();//手机号码
                                    string cust_name = ds_cust.Tables[0].Rows[0]["cust_name"].ToString();//客户姓名
                                    string surplus_bal1 = ds_card.Tables[0].Rows[0]["surplus_bal"].ToString();//卡内余额
                                    string card_no2 = ds_card.Tables[0].Rows[0]["card_no"].ToString(); //卡号
                                    if ((ismember == true) && (ds_card.Tables[0].Rows.Count > 0) && (sum_bal != "0.00"))
                                    {
                                        Model.T_Sms_Result model = new Model.T_Sms_Result();
                                        model.template_id = car_id;
                                        model.model = "carsale";
                                        model.model_type = "Buy";
                                        model.sms_date = DateTime.Now;
                                        model.sms_content = "您卡号为" + card_no2 + "的会员卡已于" + time + "消费" + sum_bal + "元。卡内余额:" + surplus_bal1 + "元[" + ds_shop.Tables[0].Rows[0]["shop_name"].ToString() + "]";
                                        model.cust_name = cust_name;
                                        model.telphone = tel;
                                        model.car_no = car_no1;
                                        model.shop_id = shop_id;
                                        SendSms.sendMessage(model);
                                    }

                                    //套餐结算时的短信提醒
                                    //暂时判断依据（cope_amt为0且T_Vip_CardServices_Rela表对应有数据的）
                                    DataSet ds_orders = DbHelperSQL.Query("select * from T_Sal_Consume_Detail where order_id='" + order_id + "' and cope_amt='0.00'");
                                    if ((ismember == true) && (ds_card.Tables[0].Rows.Count > 0) && (ds_orders.Tables[0].Rows.Count > 0))
                                    {
                                        string content = "";  //发送内容
                                        for (int oo = 0; oo < ds_orders.Tables[0].Rows.Count; oo++)
                                        {
                                            string pro_id = ds_orders.Tables[0].Rows[oo]["pro_id"].ToString();
                                            string num = ds_orders.Tables[0].Rows[oo]["number"].ToString();        //数量
                                            num = num.Substring(0, num.IndexOf(".")).ToString();
                                            string pro_name = ds_orders.Tables[0].Rows[oo]["pro_name"].ToString(); //产品名称
                                            DataSet ds_Rela = DbHelperSQL.Query("select * from T_Vip_CardServices_Rela where pro_id='" + pro_id + "' and card_id='" + card_id + "' order by add_date desc");
                                            if (ds_Rela.Tables[0].Rows.Count > 0)
                                            {
                                                int consume_cnt = int.Parse(ds_Rela.Tables[0].Rows[0]["consume_cnt"].ToString());//总次数
                                                int surplus_cnt = int.Parse(ds_Rela.Tables[0].Rows[0]["surplus_cnt"].ToString());//剩余次数
                                                content += pro_name + num + "次，还剩于" + surplus_cnt.ToString() + "次；";
                                            }
                                        }
                                        if (content != "")
                                        {
                                            Model.T_Sms_Result model = new Model.T_Sms_Result();
                                            model.template_id = car_id;
                                            model.model = "carsale";
                                            model.model_type = "Buy";
                                            model.sms_date = DateTime.Now;
                                            model.sms_content = "您卡号为" + card_no2 + "，已于" + time + "消费" + content.Substring(0, content.Length - 1) + "[" + ds_shop.Tables[0].Rows[0]["shop_name"].ToString() + "]";
                                            model.cust_name = cust_name;
                                            model.telphone = tel;
                                            model.car_no = car_no1;
                                            model.shop_id = shop_id;
                                            SendSms.sendMessage(model);
                                        }
                                    }
                                }
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
        //private void Royalty(Guid crew, T_Sal_Consume_Detail detail, decimal account_royalty, decimal account_det, T_Sal_Consume_Crew crewItem)
        //{
        //    var emp_info = BLL.T_Emp_Info.provide.GetModel(crew);
        //    if (emp_info == null || account_royalty == 0) { crewItem.royalty = 0; crewItem.royalty_amt = 0; }
        //    else
        //    {
        //        T_Emp_Royalty roy = _roy.GetItem(new QueryInfo<T_Emp_Royalty>()
        //            .SetQuery("consume_type_cd", detail.consume_type_cd).SetQuery("emp_type_cd", emp_info.emp_type_cd)
        //            .SetQuery("account_det", account_royalty).SetQuery("null_pro_id", "null"));
        //        if (roy == null) roy = _roy.GetItem(new QueryInfo<T_Emp_Royalty>()
        //                .SetQuery("consume_type_cd", detail.consume_type_cd).SetQuery("account_det", account_royalty).SetQuery("null_pro_id", "null"));
        //        T_Emp_Royalty roy_pro = _roy.GetItem(new QueryInfo<T_Emp_Royalty>()
        //            .SetQuery("consume_type_cd", detail.consume_type_cd).SetQuery("emp_type_cd", emp_info.emp_type_cd)
        //            .SetQuery("account_det", account_royalty).SetQuery("pro_id", detail.pro_id));
        //        if (roy_pro == null) roy_pro = _roy.GetItem(new QueryInfo<T_Emp_Royalty>()
        //                .SetQuery("consume_type_cd", detail.consume_type_cd).SetQuery("account_det", account_royalty).SetQuery("pro_id", detail.pro_id));
        //        if (roy != null && roy_pro == null)
        //        {
        //            crewItem.royalty = roy.royalty; crewItem.is_fix = roy.is_fix;
        //            if (UIHelper.IsBool(roy.is_fix)) crewItem.royalty_amt = roy.royalty;
        //            else crewItem.royalty_amt = roy.royalty * (decimal)0.01 * account_det;
        //        }
        //        else if (roy == null && roy_pro != null)
        //        {
        //            crewItem.royalty = roy_pro.royalty; crewItem.is_fix = roy_pro.is_fix;
        //            if (UIHelper.IsBool(roy_pro.is_fix)) crewItem.royalty_amt = roy_pro.royalty;
        //            else crewItem.royalty_amt = roy_pro.royalty * (decimal)0.01 * account_det;
        //        }
        //        else if (roy != null && roy_pro != null)
        //        {
        //            Dictionary<decimal, decimal> royalty_amt = new Dictionary<decimal, decimal>();
        //            if (UIHelper.IsBool(roy.is_fix)) royalty_amt.Add(roy.royalty, roy.royalty);
        //            else royalty_amt.Add(roy.royalty * (decimal)0.01 * account_det, roy.royalty);
        //            Dictionary<decimal, decimal> royalty_amt_pro = new Dictionary<decimal, decimal>();
        //            if (UIHelper.IsBool(roy_pro.is_fix)) royalty_amt_pro.Add(roy_pro.royalty, roy_pro.royalty);
        //            else royalty_amt_pro.Add(roy_pro.royalty * (decimal)0.01 * account_det, roy_pro.royalty);
        //            var r1 = royalty_amt.FirstOrDefault();
        //            var r2 = royalty_amt_pro.FirstOrDefault();
        //            if (r1.Key > r2.Key)
        //            {
        //                crewItem.royalty_amt = r1.Key;
        //                crewItem.royalty = r1.Value;
        //            }
        //            else
        //            {
        //                crewItem.royalty_amt = r2.Key;
        //                crewItem.royalty = r2.Value;
        //            }
        //        }
        //        else { crewItem.royalty = 0; crewItem.royalty_amt = 0; }
        //    }
        //}
        //#endregion
        #region 绑定订单信息,暂没有使用
        //private void ApplyOrderInContainer(Control container)
        //{
        //    Type type = Consume.consume.GetType();
        //    foreach (Control control in container.Controls)
        //    {
        //        var edit = control as DevExpress.Web.ASPxEditors.ASPxEdit;
        //        if (edit != null)
        //        {
        //            var p = type.GetProperty(edit.ID);
        //            if (p != null)
        //                edit.Value = p.GetValue(Consume, null);
        //        }
        //        else
        //            ApplyOrderInContainer(control);
        //    }
        //}
        #endregion
        #region 订单列表
        public IList<dynamic> BindOrderList()
        {
            var queryInfo = new QueryInfo<T_Sal_Consume>().SetQuery("amt_begin", DateTime.Parse(DateTime.Now.ToString("yyyy-MM-dd"))).SetQuery("amt_end", DateTime.Now)
                .SetQuery("shop_id", UIHelpers.UserProvide.GetCurrentShopId());
            return UIHelper.Resolve<IT_Sal_Consume>().Select(queryInfo.SetQuery("status_cd", new List<int> { 1, 2 })
                .SetQuery("no_consume_type_cd", new List<string> { "charge", "package" }));
        }
        protected void gvw_OrderDetail_Load(object sender, EventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            if (grid == null) return;
            var order_id = grid.GetMasterRowKeyValue();
            grid.DataSource = _cDetail.Select(new QueryInfo<T_Sal_Consume_Detail>().SetQuery("order_id", order_id));
            grid.DataBind();
        }
        protected void cbk_Order_Del_Callback(object sender, DevExpress.Web.ASPxCallback.CallbackEventArgs e)
        {
            string putStatus = e.Parameter;
            List<Guid> ids = gvw_Order.GetSelectedFieldValues("id").Select(p => Guid.Parse(p.ToString())).ToList();
            List<Sal_Consume> Consumes = new List<Sal_Consume>();
            foreach (Guid id in ids)
            {
                Sal_Consume Consume = new Sal_Consume();
                var consume = Consume.consume = _consume.GetItem(id);
                #region 已买单的做删除需要将被扣的会员卡金额或套餐次数还给会员
                if (consume.card_id.HasValue && Consume.consume.status_cd == "1")
                {
                    Consume.card = _card.GetItem(consume.card_id);
                    var cardServices = _cardService.GetList(new QueryInfo<T_Vip_CardServices_Rela>().SetQuery("card_id", consume.card_id).SetQuery("is_free", true)).ToList();
                    if (consume.card_bal > 0)
                    {
                        Consume.card.SetState(EntityState.Modified);
                        Consume.card.surplus_bal += consume.card_bal;
                        Consume.card.consume_amt -= consume.card_bal;
                    }
                    foreach (dynamic dyn in consume.Consume_Detail)
                    {
                        if (dyn.mode_cd == "2" && dyn.card_amt == 0 && dyn.account_det == 0)
                        {
                            var service = cardServices.FirstOrDefault(p => p.pro_id == dyn.pro_id);
                            if (service != null)
                            {
                                service.SetState(EntityState.Modified);
                                service.surplus_cnt += (int)dyn.number;
                                Consume.cardServices.Add(service);
                            }
                        }
                    }
                }
                #endregion
                Consumes.Add(Consume);
            }
            _consume.Delete(Consumes);
            e.Result = Newtonsoft.Json.JsonConvert.SerializeObject(new { success = true, msg = "删除成功" });
        }
        protected void gvw_Order_OnCustomCallback(object sender, DevExpress.Web.ASPxGridView.ASPxGridViewCustomCallbackEventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            if (e.Parameters == "Load")
                grid.DataBind();
        }
        #endregion
        protected void gvw_Product_AutoFilterCellEditorInitialize(object sender, DevExpress.Web.ASPxGridView.ASPxGridViewEditorEventArgs e)
        {
            if (e.Column.FieldName != "name")
                return;
            if (e.Editor is DevExpress.Web.ASPxEditors.ASPxTextBox)
            {
                if (Session[e.Column.FieldName + "_filter"] != null)
                {
                    ((DevExpress.Web.ASPxEditors.ASPxTextBox)e.Editor).Text = Session[e.Column.FieldName + "_filter"].ToString();
                }
                else
                {
                    ((DevExpress.Web.ASPxEditors.ASPxTextBox)e.Editor).Text = "";
                }
            }
        }

        #region 分页
        protected void gvw_Product_PageIndexChanged(object sender, EventArgs e)
        {
            var grid = sender as DevExpress.Web.ASPxGridView.ASPxGridView;
            if (grid != null) grid.DataBind();
        }

        #endregion

        protected void popupConentsPanel_Callback(object sender, DevExpress.Web.ASPxClasses.CallbackEventArgsBase e)
        {
            object cust_id = e.Parameter;

            //            string sql = @" 
            //            Select  COUNT(*)
            //                FROM T_Sal_Consume_Detail as a
            // 
            //                left join T_Sal_Consume as f on f.id=a.order_id
            //  
            //                left join T_Vip_Customer as h on h.id=f.cust_id
            //   
            //                where f.cust_id = '" + cust_id + "'";

            var queryInfo = new QueryInfo<T_Sal_Consume_Detail>();

            queryInfo.SetQuery("cust_id", cust_id);
            var details = _cDetail.Select(queryInfo);
            //最后一次消息时间
            string msg = string.Format("{0}  共到店“{1}次” 最后一次是：{2}   {3}", details.Count > 0 ? details[0].car_no : string.Empty, details.Count,
                details.Count > 0 ? details[0].update_date.ToString("yyyy-MM-dd") + "号" : "无账务记录",
                details.Count > 0 ? details[0].shop_name : string.Empty);

            this.LabMsg.Text = msg;

        }


        protected void gvw_Product_CustomCallback1(object sender, DevExpress.Web.ASPxGridView.ASPxGridViewCustomCallbackEventArgs e)
        {
            string str = e.Parameters;

            var _grid = sender as ASPxGridView;
            _grid.JSProperties["cp_msg"] = "";
            //GetListSto
            if (str == "allProd")
            {

                Session[Session.SessionID + "ServicesType"] = string.Format(" service_as!='charge' and service_as!='package' ");
                _grid.FilterExpression = "";
            }
            else
                if (str == "addProd")
                {
                    List<object> table = gvw_Product.GetSelectedFieldValues(new string[] { "id", "stock_id" });
                    if (table.Count == 0) return;
                    foreach (object[] row in table)
                    {
                        string str_proID = row[0] == null ? Guid.Empty.ToString() : row[0].ToString();
                        string str_stockID = row[1] == null ? Guid.Empty.ToString() : row[1].ToString();
                        str_stockID = str_stockID == "" ? Guid.Empty.ToString() : str_stockID;
                        Guid proID = new Guid(str_proID);
                        Guid stockID = new Guid(str_stockID);
                        LoadOrder(proID, stockID, 0);

                    }
                    gvw_OrderDetail.DataBind();
                    _grid.JSProperties["cp_msg"] = "reload";

                    //BindOrderDetail(gvw_OrderDetail);
                }
                else
                {
                    if (str.IndexOf(",") != -1)
                    {
                        if (!string.IsNullOrEmpty(str.Split(',')[0]) && str.Split(',')[0] != "allProd")
                        {
                            Session[Session.SessionID + "ServicesType"] = string.Format(" service_as='{0}'", str.Split(',')[0]);
                        }
                        else
                        {
                            Session[Session.SessionID + "ServicesType"] = string.Format(" service_as!='charge' and service_as!='package' ");
                        }
                        if (!string.IsNullOrEmpty(str.Split(',')[1]) && !str.Split(',')[1].Equals(Guid.Empty.ToString()))
                        {
                            Session[Session.SessionID + "Store"] = str.Split(',')[1];
                        }
                        else
                        {
                            Session[Session.SessionID + "Store"] = "";
                        }

                    }
                    else
                    {
                        Session[Session.SessionID + "ServicesType"] = string.Format(" service_as='{0}'", str);
                    }
                    _grid.FilterExpression = "";
                }
            //  _grid.FilterExpression = "";
            ods_Product.Select();
            _grid.DataBind();
            _grid.Selection.UnselectAll();
        }


        protected void StoreHouseDataSource_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
        {
            if (!IsPostBack)
            {
                e.InputParameters["strWhere"] = "";
            }
            else
            {
                e.InputParameters["strWhere"] = "";
            }
        }

        protected void gvw_Product_AfterPerformCallback(object sender, ASPxGridViewAfterPerformCallbackEventArgs e)
        {
            string callName = e.CallbackName;
            var grid = sender as ASPxGridView;
            grid.JSProperties["cp_clearselectValue"] = "";
            if (callName == "SELECTROWS")
            {
                grid.JSProperties["cp_selectValue"] = "";
                grid.JSProperties["cp_clearselectValue"] = "true";
            }
        }

    }
}