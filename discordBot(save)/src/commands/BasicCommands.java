package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.Vector;

import annotations.BotCom;
import annotations.ComCategory;
import annotations.ComLvl;
import annotations.ComType;
import annotations.Comparison;
import dice.DiceType;
import handy.Handler;
import handy.Tools;
import managers.CharacterManager;
import managers.PlayerManager;

public class BasicCommands {

	@BotCom(command = Handler.SETFC , lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.ADMINISTRATION)
	public void setFc(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.SETFC, Comparison.STARTS_WITH, ComLvl.NON_PLAYER)){
			Vector<String> ret = Tools.cuter(message, " ");
			try {
				PlayerManager.setPlayer4c(ret.elementAt(0), ret.elementAt(1));
				Tools.sendMessage(ret.elementAt(0) + " was " + ret.elementAt(1) + " in a previous life.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@BotCom(command = Handler.GETFC , lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.ADMINISTRATION)
	public void getFc(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.GETFC, Comparison.STARTS_WITH, ComLvl.NON_PLAYER)){
			try {
				String name = Tools.lastParameter(message, 0);
				String fc = PlayerManager.getPlayer4c(name);
				if(fc.equals("")){
					Tools.sendMessage("It seems that " + name + "'s soul is in its first cycle.");
				}
				else{
					Tools.sendMessage("It seems like " + name + " was " + fc + " in a previous life.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	@BotCom(command = GETAVATAR , lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void getAvatar(String message, String discriminator, String nick, String channel){
		if(check(nick, message, GET_VERSION, Comparison.EQUALS, ComLvl.NON_PLAYER)){

		}
	}
	 */



	@BotCom(command = Handler.GETAVATAR , lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void getAvatar(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.GETAVATAR, Comparison.STARTS_WITH, ComLvl.NON_PLAYER)){
			String target = Tools.reBuilder(-1, Tools.cuter(message, " "), " ");
			if(target.equals("me")){
				target = nick;
			}
			try {
				if(CharacterManager.doesCharacterExistFromNick(target)){
					String ret = CharacterManager.getAvatar(target);
					if(ret == null){
						Tools.sendMessage(target + " has no avatar yet.");
					}
					else{
						Tools.sendMessage(target + "'s avatar: " + ret);
					}
				}
				else{
					Tools.sendMessage("There's no " + target + " registered, " + nick + ".");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@BotCom(command = Handler.SETAVATAR , lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void setAvatar(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.SETAVATAR, Comparison.STARTS_WITH, ComLvl.NON_PLAYER)){
			String avatar = Tools.lastParameter(message, 0);

			if(avatar.contains("imgur")){
				if(avatar.contains("?")){
					avatar = avatar.substring(0, avatar.indexOf("?"));
				}
				try {
					CharacterManager.setAvatar(discriminator, nick, avatar);
					Tools.sendMessage("Your new avatar is set, " + nick + ".");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
			else{
				Tools.sendMessage("It is not an imgur link, " + nick + ".");
			}


		}
	}


	@BotCom(command = Handler.HELLO, lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void hello(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.HELLO, Comparison.EQUALS, ComLvl.NON_PLAYER)){
			Tools.sendMessage("Hello, " + nick + ".");
		}
	}

	@BotCom(command = Handler.GET_VERSION, lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void getVersion(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.GET_VERSION, Comparison.EQUALS, ComLvl.NON_PLAYER)){
			Tools.sendMessage("I am " + Handler.botName + ", version " + Handler.versionNumber + ". My current admin is " + Handler.admin + ".");
		}
	}

	@BotCom(command = Handler.SET_KEY, lvl = ComLvl.ADMIN, type = ComType.MSG, category = ComCategory.ADMINISTRATION)
	public void setKey(String message, String discriminator, String nick, String channel){

		if(Tools.check(nick, message, Handler.SET_KEY, Comparison.STARTS_WITH, ComLvl.ADMIN)){
			String temp = message.substring(Handler.key.length() + Handler.SET_KEY.length() + 1);
			if(temp.startsWith("/")){
				temp = "!";
			}
			Handler.key = temp;
			Tools.sendMessage("My new key is now " + Handler.key + ".");
		}

	}

	@BotCom(command = Handler.GET_HELP, lvl = ComLvl.NON_PLAYER, type = ComType.BOTH, category = ComCategory.BASIC)
	public void getHelp(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.GET_HELP, Comparison.EQUALS, ComLvl.NON_PLAYER)){
			Tools.sendMessage(Tools.helpMaker());
		}
	}


	@BotCom(command = Handler.GET_ACC, lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void getAcc(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.GET_ACC, Comparison.EQUALS, ComLvl.NON_PLAYER)){
			Tools.sendMessage("Your accreditation level is " + ComLvl.ADMIN.getString(Tools.levelChecker(nick).getValue()).toLowerCase() + ", " + nick + ".");
		}
	}
	@BotCom(command = Handler.YOUTUBE, lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void youtube(String message, String discriminator, String nick, String channel){
		if(Tools.check(nick, message, Handler.YOUTUBE, Comparison.STARTS_WITH, ComLvl.NON_PLAYER)){
			Vector<String> param = Tools.cuter(message, " ");

			String search = "";

			for(String str : param){
				search += str + "+";
			}
			search = search.substring(0, search.length() - 1);
			URL url;
			try {
				url = new URL("https://www.youtube.com/results?search_query=" + search);

				String ret = "";
				String merde = "/watch?v=Lxbdvo2vFwc&amp;list=PLbpi6ZahtOH4CPwU6AGz6O1HuLmt-uSgt";

				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine;
				String all = "";
				while ((inputLine = in.readLine()) != null){
					all += inputLine;
				}
				in.close();

				while(ret.equals("") || ret.equals(merde)){




					int start = all.indexOf("/watch?v=");
					int end = all.indexOf("\"", start);
					ret = all.substring(start, end);
					System.out.println(ret);
					all = all.substring(start + 8);
				}

				Tools.sendMessage("Here's the result, " + nick + " : " + "https://www.youtube.com" + ret);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@BotCom(command = Handler.ROLL, lvl = ComLvl.NON_PLAYER, type = ComType.MSG, category = ComCategory.BASIC)
	public void roll(String message, String discriminator, String nick, String channel){

		if(Tools.check(nick, message, Handler.ROLL, Comparison.STARTS_WITH, ComLvl.NON_PLAYER)){
			String param = Tools.lastParameter(message, 0).toLowerCase();
			if(param.matches("[0-9]*d[0-9]*[+-][0-9]*") || param.matches("[0-9]*d[0-9]*")){
				try{
					int dice = Integer.parseInt(param.substring(0 , param.indexOf("d")));

					int sides = -1;

					if(param.matches("[0-9]*d[0-9]*[+-][0-9]*")){
						int end = param.indexOf("+");
						if(end == -1){
							end = param.indexOf("-");
						}
						String si = param.substring((param.indexOf("d")+1), end);
						System.out.println("SI " + si);
						sides = Integer.parseInt(si);
					}
					else{
						String si2 = param.substring(param.indexOf("d")+1);
						System.out.println("SI2 " + si2);
						sides = Integer.parseInt(si2);
					}


					String overkill = "";

					if(dice > 100){
						dice = 100;
						overkill = "100 dices max";			
					}
					if(sides > 100){
						sides = 100;
						if(overkill.length()>0){
							overkill+= ", ";
						}
						overkill += "100 sides max";
					}


					DiceType dt = new DiceType(sides, dice);

					if(param.matches("[0-9]*d[0-9]*[+-][0-9]*")){
						int start = param.indexOf("+");
						if(start == -1){
							start = param.indexOf("-");
						}
						int modifier = Integer.parseInt(param.substring(start, param.length()));
						if(modifier > 100){
							if(overkill.length()>0){
								overkill+= ", ";
							}
							overkill += "100 as modifier max";
							modifier = 100;
						}
						System.out.println("MODIFIER: " + modifier);
						dt.setModifier(modifier);
					}

					if(overkill.length()>0){
						overkill += ".";
						Tools.sendMessage(overkill);
					}




					Tools.sendMessage(nick + " rolled " + dt.roll().toString());
				}
				catch(NumberFormatException e){
					System.out.println("NUMBERFORMATEXCEPTION\n " + e.getMessage());
				}
			}
			else{
				Tools.sendMessage("Your dice must be built like: xdy or xdy+z where x, y and z are integers, " + nick + ".");
			}

		}		
	}

}
