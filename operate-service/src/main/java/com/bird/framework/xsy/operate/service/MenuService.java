package com.bird.framework.xsy.operate.service;

import com.bird.framework.xsy.operate.entity.Menu;
import com.bird.framework.xsy.operate.mapper.MenuMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private SequenceService sequenceService;

    public Menu selectById(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public List<Menu> tree(Long id, List<String> roles) {
        return menuMapper.tree(id, roles);
    }

    public Page<Menu> findAll(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Menu> menus = (Page<Menu>) menuMapper.findAll();
        return menus;
    }

    public void save(Menu menu) {
        if (menu.getId() != null) {
            menuMapper.updateByPrimaryKeySelective(menu);
        }else {
            String code = sequenceService.generate("operate_menu","%08d");
            menu.setCode(code);
            menuMapper.insert(menu);
        }

    }
}
