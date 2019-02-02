package jp.co.systena.tigerscave.RPG_Similar.application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jp.co.systena.tigerscave.RPG_Similar.application.model.Job;

@Controller // Viewあり。Viewを返却するアノテーション
public class IndexController {

  @Autowired
  HttpSession session;                  // セッション管理

  /****************
   * キャラの作成
   * @param mav
   * @return
   ****************/
  @RequestMapping(value="/Fight" , method = RequestMethod.GET) // URLとのマッピング
  public ModelAndView index(ModelAndView mav) {

    Job fencer;

    // キャラをランダムで作成
    Random rnd = new Random();
    int num = rnd.nextInt(2);
    if (num == 0) {
      fencer = new Job();
    }else {
      fencer = new Job("魔法使い");
    }

    List<String> message = new ArrayList<String>();
    message.add(fencer.getName() + " を さくせい しました。");
    message.add("ＨＰは　　　" + fencer.getHit_point() + "　です");
    message.add("攻撃力は　　" + fencer.getOffensive_power() + "　です");
    message.add("防御力は　　" + fencer.getDefense_power() + "　です");
    message.add("すばやさは　" + fencer.getSpeed() + "　です");
    message.add("ランクは　　" + fencer.getRank() + "　です。");

    // セッションに作成キャラをセット
    session.setAttribute("character", fencer);

    // セッションに敵作成フラグをセット
    session.setAttribute("enemy_create_flag", "create");
    session.setAttribute("battle_status", "battle");
    session.removeAttribute("enemy");

    // messageをセット
    mav.addObject("message", message);

    // 次に遷移するControllerをセット
    String controller = "Fight";
    mav.addObject("controller", controller);

    // Viewのテンプレート名を設定
    mav.setViewName("Index");

    return mav;
  }

}