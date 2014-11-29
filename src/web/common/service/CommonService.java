package web.common.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import web.system.entity.SysUsers;

/**
 * 公共扩展方法
 * 
 * @author 张代浩
 * 
 */
/*
 * 在service类前加上@Transactional，声明这个service所有方法需要事务管理。每一个业务方法开始时都会打开一个事务。
 * 
 * Spring默认情况下会对运行期例外(RunTimeException)进行事务回滚。这个例外是unchecked
 * 
 * 如果遇到checked意外就不回滚。 原理: 其实就是利用 AOP , spring 生成了一个代理类 这个代理类加入了事务的控制来实现。
 */
/*
 * @Resource的作用相当于@Autowired，只不过@Autowired按byType自动注入，而@Resource默认按
 * byName自动注入罢了。
 * 
 * @Resource有两个属性是比较重要的，分是name和type，Spring将@Resource注解的name属性解析为bean的名字
 * ，而type属性则解析为bean的类型
 * 。所以如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略。如果既不指定name也不指定type属性
 * ，这时将通过反射机制使用byName自动注入策略。 　　@Resource装配顺序 　　1.
 * 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常 　　2.
 * 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常 　　3.
 * 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常 　　4.
 * 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；
 */
@Service("commonService")
@Transactional(propagation = Propagation.REQUIRED)
public class CommonService extends GenericBaseCommonDao {

	/**
	 * 检查用户是否存在，用户名和密码都要正确
	 * */
	public SysUsers getUserByUserIdAndUserNameExits(SysUsers user) {
		// user开始的密码是加密密码

		String query = "from User u where u.userName = :username and u.password=:passowrd";
		Query queryObject = getSession().createQuery(query);
		queryObject.setParameter("username", user.getUsername());
		queryObject.setParameter("passowrd", user.getPassword());
		List<SysUsers> users = queryObject.list();
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public void deleteByHql(String hql, String foreignKey) {
		Query query = getSession().createQuery(hql);
		query.setString(0, foreignKey);
		System.out.println(query.executeUpdate() + "行被删除");
	}
}
