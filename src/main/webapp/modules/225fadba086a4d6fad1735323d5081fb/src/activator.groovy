import org.marker.ext.ModuleActivatorimport org.marker.ext.ModuleUtilsimport org.marker.ext.module.ModuleContextimport org.marker.mushroom.ext.model.ContentModelContextimport org.marker.mushroom.ext.model.impl.OrganizationModelImpl/** * 自定义模块 *  * @author marker *  */public class ModuleActivatorImpl extends ModuleUtils implements ModuleActivator{		 	public void start(ModuleContext context) throws Exception	{		ContentModelContext.getInstance().put(new OrganizationModelImpl());		/*MenuUtils menuUtils = MenuUtils.getInstance();		// 文章菜单		def menu = [				name: "组织管理",				url : "organization/list.do",				desc: "系统基础内容管理",				icon: "fa-th",				type: "6cd7004ae4bc4aaca1435708cb7dd079" // 唯一编码		];		this.id = menuUtils.build(MenuUtils.L1_CONTENT, menu);*/			} 				public void stop(ModuleContext context) throws Exception	{		ContentModelContext.getInstance().remove("organization");	}}