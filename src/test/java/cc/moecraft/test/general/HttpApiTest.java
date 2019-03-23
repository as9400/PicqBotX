package cc.moecraft.test.general;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.test.icq.listeners.ExceptionListener;
import cc.moecraft.test.icq.listeners.TestListener;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * The class {@code HttpApiTest} is the unit tester for the http api.
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 21:05
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpApiTest
{
    private static final long TEST_RECEIVE_QQ = 233186409; // 用来测试的QQ, 必须不是机器人QQ, 而且要在群里x
    private static final long TEST_RECEIVE_GR = 491046707; // 用来测试的群

    private static PicqBotX bot;
    private static IcqHttpApi api;
    private static HyLogger logger;

    @BeforeClass
    public static void init()
    {
        // 创建机器人对象 ( 接收端口 )
        PicqBotX bot = new PicqBotX(30192);

        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot01", "127.0.0.1", 31091);

        // 注册事件
        bot.getEventManager().registerListeners(new TestListener(), new ExceptionListener());

        // 启用指令管理器, Unit test
        bot.enableCommandManager(false, "ut -");

        api = bot.getAccountManager().getNonAccountSpecifiedApi();
        logger = bot.getLogger();

        // 启动机器人
        new Thread(bot::startBot).start();
        ThreadUtil.safeSleep(2000);
    }

    @Test
    public void testGetStatus()
    {
        logger.debug(api.getStatus().toString());
    }

    @Test
    public void testGetStrangerInfo()
    {
        logger.debug(api.getStrangerInfo(TEST_RECEIVE_QQ).toString());
    }

    @Test
    public void testGetVIPInfo()
    {
        logger.debug(api.getVIPInfo(TEST_RECEIVE_QQ).toString());
    }

    @Test
    public void testGetGroupList()
    {
        logger.debug(api.getGroupList().toString());
    }

    @Test
    public void testGetGroupMemberInfo()
    {
        logger.debug(api.getGroupMemberInfo(TEST_RECEIVE_GR, TEST_RECEIVE_QQ).toString());
    }

    @Test
    public void testGetGroupInfo()
    {
        logger.debug(api.getGroupInfo(TEST_RECEIVE_GR).toString());
    }

    @Test
    public void testGetGroupMemberList()
    {
        logger.debug(api.getGroupMemberList(TEST_RECEIVE_GR).toString());
    }

    @Test
    public void testGetFriendList()
    {
        logger.debug(api.getFriendList().toString());
    }

    @Test
    public void testGetVersionInfo()
    {
        logger.debug(api.getVersionInfo().toString());
    }

    @Test
    public void testPM()
    {
        api.sendPrivateMsg(TEST_RECEIVE_QQ, "Unit test - testPM()");
    }

    @Test
    public void testPMAutoEscape()
    {
        api.sendPrivateMsg(TEST_RECEIVE_QQ, "Unit test - testPMAutoEscape()", true);
    }

    @Test
    public void testGM()
    {
        api.sendGroupMsg(TEST_RECEIVE_GR, "Unit test - testGM()");
    }

    @Test
    public void testGMAutoEscape()
    {
        api.sendGroupMsg(TEST_RECEIVE_GR, "Unit test - testGMAutoEscape()", true);
    }

    // TODO: Test DM

    @Test
    public void testDeleteMsg()
    {
        long toDelete = api.sendGroupMsg(TEST_RECEIVE_GR, "Unit test - testDeleteMsg()").getData().getMessageId();
        api.deleteMsg(toDelete);
    }

    @Test
    public void testSendLike()
    {
        api.sendLike(TEST_RECEIVE_QQ, 2);
    }

    @Test
    public void testSetGroupKick()
    {
        // 没有人可以踢哇 (╯‵□′)╯︵┻━┻
    }

    @Test
    public void testGroupBan()
    {
        api.setGroupBan(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, 1);
    }

    @Test
    public void testGroupWholeBan()
    {
        api.setGroupWholeBan(TEST_RECEIVE_GR, true);
        api.setGroupWholeBan(TEST_RECEIVE_GR, false);
    }

    @Test
    public void testSetGroupAdmin()
    {
        api.setGroupAdmin(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, true);
        api.setGroupAdmin(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, false);
    }

    @Test
    public void testSetGroupAnonymous()
    {
        api.setGroupAnonymous(TEST_RECEIVE_GR, false);
        api.setGroupAnonymous(TEST_RECEIVE_GR, true);
    }

    @Test
    public void testSetGroupCard()
    {
        api.setGroupCard(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, "UT-GC");
    }

    // TODO: setGroupLeave

    @Test
    public void testSetGroupSpecialTitle()
    {
        api.setGroupSpecialTitle(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, "UT-GC", 200);
    }
}