package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.WayFido2RelyParty;
import com.stylefeng.guns.modular.system.service.IWayFido2RelyPartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/fidorest/rely_party")
public class FidoRestRelyPartyController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FidoRestRelyPartyController.class);
    private static final String Tag = "FidoRestRelyPartyController";

    private static String PREFIX = "/fidorest/rely_party/";

    @Autowired
    private HttpServletRequest request;

    @Value("${spring.profiles.active}")
    private String isPro;

    @Value("${u2f-rest.local-url}")
    private String localURL;

    @Value("${u2f-rest.dev-url}")
    private String devURL;

    @Value("${u2f-rest.pro-url}")
    private String proURL;
    @Autowired
    private IWayFido2RelyPartyService iWayFido2RelyPartyService;

    @RequestMapping("")
    public String index() {
        return PREFIX + "rely_party.html";
    }

    @RequestMapping("add")
    public String addRelyParty() {

        return PREFIX + "rely_party_add.html";
    }

    /**
     * 获取依赖方列表.
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public Object getRelyPartys(@RequestParam(required = false) String rpName) {
        Page<WayFido2RelyParty> page = new PageFactory<WayFido2RelyParty>().defaultPage();

        List<WayFido2RelyParty> result = iWayFido2RelyPartyService.selectAllRelyParty(page,
                rpName, page.getOrderByField(), page.isAsc());

        page.setTotal(page.getTotal());
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "addRelyParty", method = RequestMethod.POST)
    @ResponseBody
    public Tip addRelyParty(WayFido2RelyParty wayFido2RelyParty) {
        logger.info(Tag + "/addRelyParty: \n wayFido2RelyParty: {}", wayFido2RelyParty);

        Integer integer = iWayFido2RelyPartyService.selectRpByRpName(wayFido2RelyParty.getRpName());
        if (Optional.ofNullable(integer).isPresent() && integer > 0) {
            return new ErrorTip(201, wayFido2RelyParty.getRpName() + " already exists!");
        }
        wayFido2RelyParty.setAddTime(new Date());
        iWayFido2RelyPartyService.addRelyParty(wayFido2RelyParty);

        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "{rpId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Tip deleteRelyParty(@PathVariable(value = "rpId") Integer rpId) {
        logger.info(Tag + "/deleteRelyParty: \n rpId: {}", rpId);
        iWayFido2RelyPartyService.deleteById(rpId);
        return SUCCESS_TIP;
    }

    /**
     * 修改页面
     */
    @RequestMapping("/edit/{id}")
    public String editSystem(@PathVariable("id") Integer id, Model model) {
        WayFido2RelyParty wayFido2RelyParty = iWayFido2RelyPartyService.selectById(id);
        model.addAttribute("wayFido2RelyParty",wayFido2RelyParty);
        LogObjectHolder.me().set(wayFido2RelyParty);
        return PREFIX + "rely_party_edit.html";
    }

    /**
     * 提交修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Tip updateRelyParty(WayFido2RelyParty wayFido2RelyParty) {
    	logger.info(Tag + "/updateRelyParty: \n wayFido2RelyParty: {}", wayFido2RelyParty);
    	
        if (Optional.ofNullable(wayFido2RelyParty).isPresent()) {
        	Integer count = iWayFido2RelyPartyService.selectRpByRpName(wayFido2RelyParty.getRpName());
        	if (count > 1) {
				return new ErrorTip(201, wayFido2RelyParty.getRpName() + " already exists!");
			}
            wayFido2RelyParty.setUpdateTime(new Date());
            iWayFido2RelyPartyService.updateById(wayFido2RelyParty);
        }
        return SUCCESS_TIP;
    }
}
