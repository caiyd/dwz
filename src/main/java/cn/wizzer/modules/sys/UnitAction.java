package cn.wizzer.modules.sys;

import cn.wizzer.common.Message;
import cn.wizzer.common.annotation.SLog;
import cn.wizzer.common.mvc.filter.PrivateFilter;
import cn.wizzer.common.util.StringUtils;
import cn.wizzer.modules.sys.bean.Sys_unit;
import cn.wizzer.modules.sys.service.UnitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Wizzer.cn on 2015/7/4.
 */
@IocBean
@At("/private/sys/unit")
@Filters({@By(type = PrivateFilter.class)})
@SLog(tag = "机构管理", msg = "")
public class UnitAction {
    @Inject
    UnitService unitService;

    @At("")
    @Ok("vm:template.private.sys.unit.index")
    @RequiresPermissions("sys:unit")
    @SLog(tag = "机构列表", msg = "访问机构列表")
    public Object index() {
        return unitService.query(Cnd.where("parentId", "=", "").asc("location").asc("path"), null);
    }

    @At("/add")
    @Ok("vm:template.private.sys.unit.add")
    @RequiresPermissions("sys:unit")
    public void add(@Param("pid") String pid, HttpServletRequest req) {
        if (!Strings.isEmpty(pid)) {
            req.setAttribute("parentUnit", unitService.fetch(pid));
        }
    }

    @At("/add/do")
    @Ok("json")
    @RequiresPermissions("sys:unit")
    @SLog(tag = "新增机构", msg = "机构名称：${args[0].name}")
    public Object addDo(@Param("..") Sys_unit unit, @Param("parentId") String parentId, HttpServletRequest req) {
        int sum = unitService.count(Cnd.where("parentId", "=", parentId).and("name", "=", unit.getName()));
        if (sum > 0) {
            return Message.error("机构名称已存在！", req);
        }
        try {
            unitService.save(unit, parentId);
            return Message.success("system.success", req);
        } catch (Exception e) {
            return Message.error("system.error", req);
        }
    }

    @At("/edit/?")
    @Ok("vm:template.private.sys.unit.edit")
    @RequiresPermissions("sys:unit")
    public Object edit(String id, HttpServletRequest req) {
        Sys_unit unit = unitService.fetch(id);
        if (unit != null && !Strings.isEmpty(unit.getParentId())) {
            req.setAttribute("parentUnit", unitService.fetch(unit.getParentId()));
        }
        return unit;
    }

    @At("/edit/do")
    @Ok("json")
    @RequiresPermissions("sys:unit")
    @SLog(tag = "修改机构", msg = "机构名称：${args[0].name}")
    public Object editDo(@Param("..") Sys_unit unit, @Param("pid") String pid, HttpServletRequest req) {
        if (unit.getParentId().equals(unit.getId())) {
            return Message.error("上级机构不可为自身！", req);
        }
        if (!Strings.sNull(pid).equals(unit.getParentId())) {
            int sum = unitService.count(Cnd.where("parentId", "=", unit.getParentId()).and("name", "=", unit.getName()));
            if (sum > 0) {
                return Message.error("机构名称已存在！", req);
            }
        }
        try {
            unitService.edit(unit, pid);
            return Message.success("system.success", req);
        } catch (Exception e) {
            return Message.success("system.error", req);
        }
    }

    @At("/detail/?")
    @Ok("vm:template.private.sys.unit.detail")
    @RequiresPermissions("sys:unit")
    public Object detail(String id, HttpServletRequest req) {
        Sys_unit unit = unitService.fetch(id);
        if (unit != null && !Strings.isEmpty(unit.getParentId())) {
            req.setAttribute("parentUnit", unitService.getField("name", unit.getParentId()).getName());
        }
        return unit;
    }

    @At("/child/?")
    @Ok("vm:template.private.sys.unit.child")
    @RequiresPermissions("sys:unit")
    public Object child(String id, HttpServletRequest req) {
        return unitService.query(Cnd.where("parentId", "=", id), null);
    }

    @At("/tree")
    @Ok("json")
    @RequiresPermissions("sys:unit")
    public Object tree(@Param("pid") String pid, HttpServletRequest req) {
        List<Record> list;
        if (!Strings.isEmpty(pid)) {
            list = unitService.list(Sqls.create("select id,name as text,has_children as children from sys_unit where parentId =@pid order by location asc,path asc").setParam("pid",pid));
        } else {
            list = unitService.list(Sqls.create("select id,name as text,has_children as children from sys_unit where length(path)=4 order by location asc,path asc"));
        }
        return list;
    }

    @At("/delete/?")
    @Ok("json")
    @RequiresPermissions("sys:unit")
    @SLog(tag = "删除机构", msg = "机构名称：${args[1].getAttribute('name')}")
    public Object delete(String id, HttpServletRequest req) {
        Sys_unit unit = unitService.fetch(id);
        req.setAttribute("name", unit.getName());
        if ("0001".equals(unit.getPath())) {
            return Message.error("system.not.allow", req);
        }
        try {
            unitService.deleteAndChild(id);
            return Message.success("system.success", req);
        } catch (Exception e) {
            return Message.error("system.error", req);
        }
    }
}
