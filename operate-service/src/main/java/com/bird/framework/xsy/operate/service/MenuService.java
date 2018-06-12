package com.bird.framework.xsy.operate.service;

import com.bird.framework.xsy.operate.entity.Menu;
import com.bird.framework.xsy.operate.mapper.MenuMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("operateMenuService")
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    @Qualifier("operateSequenceService")
    private SequenceService sequenceService;

    public Menu selectById(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public List<Menu> tree(Long id) {
        return menuMapper.tree(id);
    }

    public List<Map<String, Object>> allByRole(String code) {
        return menuMapper.allByRole(code);
    }

    public List<Menu> privileges(Long id, List<String> roles) {
        return menuMapper.privileges(id, roles);
    }

    public Page<Menu> page(Long id, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Menu> menus = (Page<Menu>) menuMapper.findAll(id);
        return menus;
    }

    public void save(Menu menu) {
        if (menu.getId() != null) {
            menuMapper.updateByPrimaryKeySelective(menu);
        } else {
            String code = sequenceService.generate("operate_menu", "%08d");
            menu.setCode(code);
            menuMapper.insert(menu);
        }
    }

    public void delete(Long id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
