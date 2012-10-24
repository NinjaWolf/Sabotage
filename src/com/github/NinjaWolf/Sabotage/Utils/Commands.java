package com.github.NinjaWolf.Sabotage.Utils;

import org.bukkit.command.CommandSender;

import com.github.NinjaWolf.Sabotage.Commands.StCommand;

public abstract class Commands
implements StCommand
{
private final String name;
private String description = "";
private String usage = "";
private String permission = "";
private String[] notes = new String[0];
private String[] identifiers = new String[0];
private int minArguments = 0;
private int maxArguments = 0;

public Commands(String name) {
  this.name = name;
}

public void cancelInteraction(CommandSender executor)
{
}

public String getDescription()
{
  return this.description;
}

public String[] getIdentifiers()
{
  return this.identifiers;
}

public int getMaxArguments()
{
  return this.maxArguments;
}

public int getMinArguments()
{
  return this.minArguments;
}

public String getName()
{
  return this.name;
}

public String[] getNotes()
{
  return this.notes;
}

public String getPermission()
{
  return this.permission;
}

public String getUsage()
{
  return this.usage;
}

public boolean isIdentifier(CommandSender executor, String input)
{
  for (String identifier : this.identifiers) {
    if (input.equalsIgnoreCase(identifier)) {
      return true;
    }
  }
  return false;
}

public boolean isInProgress(CommandSender executor)
{
  return false;
}

public boolean isInteractive()
{
  return false;
}

public boolean isShownOnHelpMenu()
{
  return true;
}

public void setArgumentRange(int min, int max) {
  this.minArguments = min;
  this.maxArguments = max;
}

public void setDescription(String description) {
  this.description = description;
}

public void setIdentifiers(String[] identifiers) {
  this.identifiers = identifiers;
}

public void setNotes(String[] notes) {
  this.notes = notes;
}

public void setPermission(String permission) {
  this.permission = permission;
}

public void setUsage(String usage) {
  this.usage = usage;
}
}