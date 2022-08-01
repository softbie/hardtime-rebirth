;//////////////////////////////////////////////////////////////////////////////
;---------------------------- HARD TIME: DATA ---------------------------------
;//////////////////////////////////////////////////////////////////////////////

;------------------------------------------------------------------------
;///////////////////////////// OPTIONS //////////////////////////////////
;------------------------------------------------------------------------

;SAVE OPTIONS
Function SaveOptions()
 file=WriteFile("Data/Options.dat")
  ;preferences
  WriteInt file,optRes
  WriteInt file,optPopulation
  WriteInt file,optFog
  WriteInt file,optShadows
  WriteInt file,optFX
  WriteInt file,optGore
  ;key controls
  WriteInt file,keyAttack
  WriteInt file,keyDefend
  WriteInt file,keyThrow
  WriteInt file,keyPickUp
  ;gamepad controls
  WriteInt file,buttAttack
  WriteInt file,buttDefend
  WriteInt file,buttThrow
  WriteInt file,buttPickUp
  ;game ID's
  For count=1 To 3
   WriteString file,gamName$(count)
  Next
 CloseFile(file)
End Function

;LOAD OPTIONS
Function LoadOptions()
 file=ReadFile("Data/Options.dat")
  ;preferences
  optRes=ReadInt(file)
  optPopulation=ReadInt(file)
  optFog=ReadInt(file)
  optShadows=ReadInt(file)
  optFX=ReadInt(file)
  optGore=ReadInt(file)
  ;key controls
  keyAttack=ReadInt(file)
  keyDefend=ReadInt(file)
  keyThrow=ReadInt(file)
  keyPickUp=ReadInt(file)
  ;gamepad controls
  buttAttack=ReadInt(file)
  buttDefend=ReadInt(file)
  buttThrow=ReadInt(file)
  buttPickUp=ReadInt(file)
  ;game ID's
  For count=1 To 3
   gamName$(count)=ReadString(file)
  Next
 CloseFile(file)
End Function

;------------------------------------------------------------------------
;///////////////////////////// PROGRESS /////////////////////////////////
;------------------------------------------------------------------------

;SAVE PROGRESS
Function SaveProgress()
 file=WriteFile("Data/Slot0"+slot+"/Progress.dat")
  ;status
  WriteInt file,no_chars
  WriteInt file,gamChar(slot)
  WriteInt file,gamPlayer(slot)
  WriteInt file,gamLocation(slot)
  WriteInt file,gamMoney(slot)
  ;time
  WriteInt file,gamSpeed(slot)
  WriteInt file,gamSecs(slot)
  WriteInt file,gamMins(slot)
  WriteInt file,gamHours(slot)
  ;missions
  WriteInt file,gamMission(slot)
  WriteInt file,gamClient(slot)
  WriteInt file,gamTarget(slot)
  WriteInt file,gamDeadline(slot)
  WriteInt file,gamReward(slot)
  ;handles
  WriteInt file,gamWarrant(slot)
  WriteInt file,gamVictim(slot)
  WriteInt file,gamItem(slot)
  WriteInt file,gamArrival(slot)
  WriteInt file,gamFatality(slot)
  WriteInt file,gamRelease(slot)
  WriteInt file,gamEscape(slot)
  WriteInt file,gamGrowth(slot)
  WriteInt file,gamBlackout(slot)
  WriteInt file,gamBombThreat(slot)
  ;promos
  WriteInt file,phonePromo
  For count=1 To 300
   WriteInt file,promoUsed(count)
  Next
  ;camera
  WriteFloat file,camX#
  WriteFloat file,camY#
  WriteFloat file,camZ#
  WriteFloat file,camPivX#
  WriteFloat file,camPivY#
  WriteFloat file,camPivZ#
  ;atmosphere
  WriteFloat file,lightR#
  WriteFloat file,lightG#
  WriteFloat file,lightB#
  WriteFloat file,ambR#
  WriteFloat file,ambG#
  WriteFloat file,ambB#
  WriteFloat file,atmosR#
  WriteFloat file,atmosG#
  WriteFloat file,atmosB#
  WriteFloat file,skyR#
  WriteFloat file,skyG#
  WriteFloat file,skyB#
  ;dinner trays
  For count=1 To 50
   WriteInt file,trayState(count)
  Next
 CloseFile(file)
End Function

;LOAD PROGRESS
Function LoadProgress()
 file=ReadFile("Data/Slot0"+slot+"/Progress.dat")
  ;status
  no_chars=ReadInt(file)
  gamChar(slot)=ReadInt(file)
  gamPlayer(slot)=ReadInt(file)
  gamLocation(slot)=ReadInt(file)
  gamMoney(slot)=ReadInt(file)
  ;time
  gamSpeed(slot)=ReadInt(file)
  gamSecs(slot)=ReadInt(file)
  gamMins(slot)=ReadInt(file)
  gamHours(slot)=ReadInt(file)
  ;missions
  gamMission(slot)=ReadInt(file)
  gamClient(slot)=ReadInt(file)
  gamTarget(slot)=ReadInt(file)
  gamDeadline(slot)=ReadInt(file)
  gamReward(slot)=ReadInt(file)
  ;handles
  gamWarrant(slot)=ReadInt(file)
  gamVictim(slot)=ReadInt(file)
  gamItem(slot)=ReadInt(file)
  gamArrival(slot)=ReadInt(file)
  gamFatality(slot)=ReadInt(file)
  gamRelease(slot)=ReadInt(file)
  gamEscape(slot)=ReadInt(file)
  gamGrowth(slot)=ReadInt(file)
  gamBlackout(slot)=ReadInt(file)
  gamBombThreat(slot)=ReadInt(file)
  ;promos
  phonePromo=ReadInt(file)
  For count=1 To 300
   promoUsed(count)=ReadInt(file)
  Next
  ;camera
  camX#=ReadFloat(file)
  camY#=ReadFloat(file)
  camZ#=ReadFloat(file)
  camPivX#=ReadFloat(file)
  camPivY#=ReadFloat(file)
  camPivZ#=ReadFloat(file)
  ;atmosphere
  lightR#=ReadFloat(file)
  lightG#=ReadFloat(file)
  lightB#=ReadFloat(file)
  ambR#=ReadFloat(file)
  ambG#=ReadFloat(file)
  ambB#=ReadFloat(file)
  atmosR#=ReadFloat(file)
  atmosG#=ReadFloat(file)
  atmosB#=ReadFloat(file)
  skyR#=ReadFloat(file)
  skyG#=ReadFloat(file)
  skyB#=ReadFloat(file)
  ;dinner trays
  For count=1 To 50
   trayState(count)=ReadInt(file)
  Next
 CloseFile(file)
End Function

;------------------------------------------------------------------------
;//////////////////////////// CHARACTERS ////////////////////////////////
;------------------------------------------------------------------------

;SAVE CHARACTERS
Function SaveChars()
 For char=1 To no_chars
  file=WriteFile("Data/Slot0"+slot+"/Character"+Dig$(char,100)+".dat")
   ;appearance
   WriteString file,charName$(char)
   WriteInt file,charSnapped(char)
   WriteInt file,charModel(char)
   WriteInt file,charHeight(char)
   WriteInt file,charSpecs(char)
   WriteInt file,charAccessory(char)
   WriteInt file,charHairStyle(char)
   WriteInt file,charHair(char)
   WriteInt file,charFace(char)
   WriteInt file,charCostume(char)
   For count=1 To 40
    WriteInt file,charScar(char,count)
   Next
   ;attributes
   WriteInt file,charHealth(char)
   WriteInt file,charHP(char)
   WriteInt file,charInjured(char) 
   WriteInt file,charStrength(char)
   WriteInt file,charAgility(char)
   WriteInt file,charHappiness(char)
   WriteInt file,charBreakdown(char)
   WriteInt file,charIntelligence(char)
   WriteInt file,charReputation(char)
   WriteInt file,charWeapon(char)
   For count=1 To 30
    WriteInt file,charWeapHistory(char,count)
   Next
   ;status
   WriteInt file,charRole(char)
   WriteInt file,charSentence(char)
   WriteInt file,charCrime(char)
   WriteInt file,charLocation(char)
   WriteInt file,charBlock(char)
   WriteInt file,charCell(char)
   WriteInt file,charExperience(char)
   WriteFloat file,charX#(char)
   WriteFloat file,charY#(char)
   WriteFloat file,charZ#(char)
   WriteFloat file,charA#(char)
   ;relationships
   WriteInt file,charGang(char)
   For gang=1 To 6
    WriteInt file,charGangHistory(char,gang)
   Next
   WriteInt file,charAttacker(char)
   WriteInt file,charWitness(char)
   WriteInt file,charPromoRef(char)
   WriteInt file,charFollowTim(char)
   WriteInt file,charBribeTim(char)
   For v=1 To no_chars
    WriteInt file,charRelation(char,v)
    WriteInt file,charAngerTim(char,v)
    WriteInt file,charPromo(char,v)
   Next 
  CloseFile(file)
 Next
End Function

;LOAD CHARACTERS
Function LoadChars()
 For char=1 To no_chars
  file=ReadFile("Data/Slot0"+slot+"/Character"+Dig$(char,100)+".dat")
   ;appearance
   charName$(char)=ReadString(file)
   charSnapped(char)=ReadInt(file)
   charModel(char)=ReadInt(file)
   charHeight(char)=ReadInt(file)
   charSpecs(char)=ReadInt(file)
   charAccessory(char)=ReadInt(file)
   charHairStyle(char)=ReadInt(file)
   charHair(char)=ReadInt(file)
   charFace(char)=ReadInt(file)
   charCostume(char)=ReadInt(file)
   For count=1 To 40
    charScar(char,count)=ReadInt(file)
   Next
   ;attributes
   charHealth(char)=ReadInt(file)
   charHP(char)=ReadInt(file)
   charInjured(char)=ReadInt(file) 
   charStrength(char)=ReadInt(file)
   charAgility(char)=ReadInt(file)
   charHappiness(char)=ReadInt(file)
   charBreakdown(char)=ReadInt(file)
   charIntelligence(char)=ReadInt(file)
   charReputation(char)=ReadInt(file)
   charWeapon(char)=ReadInt(file)
   For count=1 To 30
    charWeapHistory(char,count)=ReadInt(file)
   Next
   ;status
   charRole(char)=ReadInt(file)
   charSentence(char)=ReadInt(file)
   charCrime(char)=ReadInt(file)
   charLocation(char)=ReadInt(file)
   charBlock(char)=ReadInt(file)
   charCell(char)=ReadInt(file)
   charExperience(char)=ReadInt(file)
   charX#(char)=ReadFloat(file)
   charY#(char)=ReadFloat(file)
   charZ#(char)=ReadFloat(file)
   charA#(char)=ReadFloat(file)
   ;relationships
   charGang(char)=ReadInt(file)
   For gang=1 To 6
    charGangHistory(char,gang)=ReadInt(file)
   Next
   charAttacker(char)=ReadInt(file)
   charWitness(char)=ReadInt(file)
   charPromoRef(char)=ReadInt(file)
   charFollowTim(char)=ReadInt(file)
   charBribeTim(char)=ReadInt(file)
   For v=1 To no_chars
    charRelation(char,v)=ReadInt(file)
    charAngerTim(char,v)=ReadInt(file)
    charPromo(char,v)=ReadInt(file)
   Next 
  CloseFile(file)
 Next 
End Function

;LOAD PHOTOS
Function LoadPhotos()
 Loader("Please Wait","Loading Photos") 
 For char=1 To no_chars
  charPhoto(char)=0
  If charSnapped(char)>0
   charPhoto(char)=LoadImage("Data/Slot0"+slot+"/Photos/Photo"+Dig$(char,100)+".bmp")
   If charPhoto(char)>0 Then MaskImage charPhoto(char),255,0,255
   If charPhoto(char)=0 Then charSnapped(char)=0
  EndIf
 Next
End Function

;SAVE PHOTOS
Function SavePhotos()
 If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Saving Photos") 
 For char=1 To no_chars
  If charPhoto(char)>0
   SaveImage(charPhoto(char),"Data/Slot0"+slot+"/Photos/Photo"+Dig$(char,100)+".bmp")
  EndIf
 Next
End Function

;------------------------------------------------------------------------
;////////////////////////////// WEAPONS /////////////////////////////////
;------------------------------------------------------------------------

;SAVE ITEMS
Function SaveItems()
 file=WriteFile("Data/Slot0"+slot+"/Items.dat")
  ;weapons
  WriteInt file,no_weaps
  For cyc=1 To no_weaps
   WriteInt file,weapType(cyc)
   WriteInt file,weapState(cyc)
   WriteInt file,weapLocation(cyc)
   WriteFloat file,weapX#(cyc)
   WriteFloat file,weapY#(cyc)
   WriteFloat file,weapZ#(cyc)
   WriteFloat file,weapA#(cyc)
   WriteInt file,weapCarrier(cyc)
   WriteInt file,weapClip(cyc)
   WriteInt file,weapAmmo(cyc)
   WriteInt file,weapScar(cyc)
  Next
  ;kits
  For count=1 To 6
   WriteInt file,kitType(count)
   WriteInt file,kitState(count)
  Next
 CloseFile(file)
End Function

;LOAD ITEMS
Function LoadItems()
 file=ReadFile("Data/Slot0"+slot+"/Items.dat")
  ;weapons
  no_weaps=ReadInt(file)
  For cyc=1 To no_weaps
   weapType(cyc)=ReadInt(file)
   weapState(cyc)=ReadInt(file)
   weapLocation(cyc)=ReadInt(file)
   weapX#(cyc)=ReadFloat(file)
   weapY#(cyc)=ReadFloat(file)
   weapZ#(cyc)=ReadFloat(file)
   weapA#(cyc)=ReadFloat(file)
   weapCarrier(cyc)=ReadInt(file)
   weapClip(cyc)=ReadInt(file)
   weapAmmo(cyc)=ReadInt(file)
   weapScar(cyc)=ReadInt(file)
  Next
  ;kits
  For count=1 To 6
   kitType(count)=ReadInt(file)
   kitState(count)=ReadInt(file)
  Next
 CloseFile(file)
End Function

;////////////////////////////////////////////////////////////////
;---------------------- RELATED FUNCTIONS -----------------------
;////////////////////////////////////////////////////////////////

;INITIATE NEW GAME
Function GenerateGame() 
 ;initiate characters
 no_chars=optPopulation+3
 no_wardens=optPopulation/5
 If no_wardens<10 Then no_wardens=10
 For char=1 To no_chars
  charRole(char)=0 : charLocation(char)=0
  charBlock(char)=0 : charCell(char)=0
  charName$(char)="Character"+Dig$(char,100)
 Next
 gamChar(slot)=Rnd(no_wardens+4,no_chars)
 For char=1 To no_chars
  If char=<2 Then GenerateCharacter(char,2)
  If char=3 Then GenerateCharacter(char,3) 
  If char=>4
   If char-3=<no_wardens
    GenerateCharacter(char,1)
   Else
    GenerateCharacter(char,0) 
   EndIf
  EndIf
 Next
 ;reset player status
 char=gamChar(slot)
 charHealth(char)=50 
 charStrength(char)=50
 charAgility(char)=50
 charHappiness(char)=50
 charIntelligence(char)=50
 charReputation(char)=50
 gamMoney(slot)=0
 ;reset clock
 gamSecs(slot)=0
 gamMins(slot)=0
 gamHours(slot)=Rnd(8,20)
 ;missions
 gamMission(slot)=0
 gamClient(slot)=0
 gamTarget(slot)=0
 gamDeadline(slot)=0
 gamReward(slot)=0
 ;reset game status
 gamWarrant(slot)=0
 gamArrival(slot)=0
 gamFatality(slot)=0
 gamRelease(slot)=0
 gamEscape(slot)=0
 gamGrowth(slot)=0
 ;reset promos
 For count=1 To no_promos
  promoUsed(count)=0
 Next
 ;find cell mates
 FindCellMates()
 ;initial location
 charLocation(char)=9
 gamLocation(slot)=charLocation(char)
 charX#(char)=0 : charZ#(char)=0
 camX#=0 : camY#=75 : camZ#=0 
 camPivX#=camX# : camPivY#=camY# : camPivZ#=camZ#
 ;generate weapons
 no_weaps=optPopulation
 For cyc=1 To no_weaps
  If cyc=1 Then GenerateWeapon(cyc,14,2,Rnd(-30,100),50,Rnd(270,425))
  If cyc=2 Or cyc=3 Then GenerateWeapon(cyc,5,9,Rnd(-30,40),50,Rnd(235,280))
  If cyc=>4 Then GenerateWeapon(cyc,0,0,0,0,0)
 Next
 ;distribute weapons
 For cyc=1 To no_weaps
  If weapLocation(cyc)>0 And (weapType(cyc)=7 Or weapType(cyc)=8 Or weapType(cyc)=12)
   For v=1 To no_chars 
    If charRole(v)=1 And charWeapon(v)=0 And FindCarrier(cyc)=0
     charWeapon(v)=cyc : weapLocation(cyc)=charLocation(v)
     weapX#(cyc)=charX#(v) : weapY#(cyc)=charY#(v)+10 : weapZ#(cyc)=charZ#(v)
    EndIf
   Next
  EndIf
  If weapLocation(cyc)>0
   For v=1 To no_chars
    randy=Rnd(0,100) 
    If randy=0 And charRole(v)=0 And v<>gamChar(slot) And charWeapon(v)=0 And FindCarrier(cyc)=0 And weapState(cyc)>0
     charWeapon(v)=cyc : weapLocation(cyc)=charLocation(v)
     weapX#(cyc)=charX#(v) : weapY#(cyc)=charY#(v)+10 : weapZ#(cyc)=charZ#(v)
    EndIf
   Next
  EndIf
 Next
 ;generate kits
 For cyc=1 To 6
  Repeat
   kitType(cyc)=Rnd(1,weapList)
  Until weapCreate(kitType(cyc))=1
  randy=Rnd(0,2)
  If randy=<1 Then kitState(cyc)=1
 Next
 ;save generation
 SaveProgress()
 SaveChars()
 SavePhotos()
 SaveItems()
End Function

;GENERATE CHARACTER
Function GenerateCharacter(char,role)
 ;appearance
 charRole(char)=role
 charName$(char)=GenerateName$(char)
 charPhoto(char)=0 : charSnapped(char)=0
 randy=Rnd(0,5)
 If randy=0 Then charModel(char)=2 Else charModel(char)=Rnd(1,no_models)
 randy=Rnd(0,2)
 If randy=0 Then charHeight(char)=Rnd(10,15) Else charHeight(char)=Rnd(5,24)
 charSpecs(char)=Rnd(-10,4)
 If charSpecs(char)<0 Then charSpecs(char)=0
 randy=Rnd(0,2)
 If randy=0 And charRole(char)=1 Then charAccessory(char)=7 Else charAccessory(char)=0
 charFace(char)=Rnd(1,no_faces) 
 charHair(char)=Rnd(1,no_hairs)
 randy=Rnd(0,2)
 If randy=<1 And charHair(char)=>8 Then charHair(char)=Rnd(1,7)
 If GetRace(char)=1 And charHair(char)=>3 And charHair(char)=<7 Then charHair(char)=Rnd(1,2)
 If GetRace(char)=2 And charHair(char)=>2 And charHair(char)=<7 Then charHair(char)=1
 charHairStyle(char)=Rnd(-30,31)
 If charHairStyle(char)<0 Or charRole(char)=>2 Then charHairStyle(char)=Rnd(0,10)
 charCostume(char)=Rnd(0,8)
 If charRole(char)=1 Then charCostume(char)=5
 If charRole(char)=>2 Then charCostume(char)=7
 charWeapon(char)=0
 For count=1 To 30
  charWeapHistory(char,count)=0
 Next
 For limb=1 To 40
  charScar(char,limb)=0
 Next
 ;inmate location
 If charRole(char)=0
  AssignCell(char)
  charLocation(char)=TranslateBlock(charBlock(char))
  charX#(char)=GetCentre#(cellX1#(charCell(char)),cellX2#(charCell(char)))
  charZ#(char)=GetCentre#(cellZ1#(charCell(char)),cellZ2#(charCell(char)))
  charY#(char)=cellY1#(charCell(char))+20 : charA#(char)=Rnd(0,360)
 EndIf
 ;warden location
 If charRole(char)=1
  its=0
  Repeat
   area=Rnd(1,10) : its=its+1
  Until AreaPopulation(area,1)=0 Or its>100
  charLocation(char)=area
  charX#(char)=Rnd(-100,100) : charZ#(char)=Rnd(-100,100)
  If charLocation(char)=2 Then charX#(char)=Rnd(250,450) : charZ#(char)=Rnd(250,450)
  charY#(char)=50 : charA#(char)=Rnd(0,360)
 EndIf
 ;attributes
 charHealth(char)=Rnd(10,100) : charHP(char)=10 
 If charModel(char)=1 Then charStrength(char)=Rnd(40,70) : charAgility(char)=Rnd(70,100) 
 If charModel(char)=2 Then charStrength(char)=Rnd(50,80) : charAgility(char)=Rnd(60,90)
 If charModel(char)=3 Then charStrength(char)=Rnd(60,90) : charAgility(char)=Rnd(60,90)
 If charModel(char)=>4 Then charStrength(char)=Rnd(60,90) : charAgility(char)=Rnd(50,80)
 charStrength(char)=charStrength(char)+(charHeight(char)/2)
 charAgility(char)=charAgility(char)-(charHeight(char)/2)
 charHappiness(char)=Rnd(10,100)
 charIntelligence(char)=Rnd(50,100)
 charReputation(char)=Rnd(50,100)
 If charRole(char)>0 Then charIntelligence(char)=Rnd(70,100) : charReputation(char)=Rnd(70,100)
 If charRole(char)>0 Then charSentence(char)=0 Else charSentence(char)=Rnd(1,365)
 If charRole(char)>0 Then charCrime(char)=0 Else charCrime(char)=Rnd(1,15)
 charExperience(char)=0
 ;gang membership
 For gang=1 To 6
  charGangHistory(char,gang)=0
 Next
 charGang(char)=Rnd(-1,6) 
 If charGang(char)<0 Or charRole(char)>0 Or char=gamChar(slot) Then charGang(char)=0
 If charGang(char)=1 And GetRace(char)>0 Then charGang(char)=0
 If charGang(char)=2 And GetRace(char)<>1 Then charGang(char)=0
 If charGang(char)=3 And GetRace(char)<>2 Then charGang(char)=0
 If charGang(char)=4 And charIntelligence(char)<70 Then charGang(char)=0
 If charGang(char)=5 And charStrength(char)+charAgility(char)<140 Then charGang(char)=0
 If charGang(char)=6 And charReputation(char)>80 Then charGang(char)=0
 If charGang(char)>0 Then charGangHistory(char,charGang(char))=1
 GangAdjust(char)
 ;relationships
 For v=1 To no_chars
  ChangeRelationship(char,v,0)
  If char<>gamChar(slot) And v<>gamChar(slot)
   randy=Rnd(0,20)
   If randy=0 Then ChangeRelationship(char,v,1)
   If randy=1 Then ChangeRelationship(char,v,-1)
   If randy=<5 And charRole(char)=1 And charRole(v)=1 Then ChangeRelationship(char,v,1)
   If randy=<5 And charGang(char)>0 And charGang(cyc)=charGang(v) Then ChangeRelationship(char,v,1)
  EndIf
  charAngerTim(char,v)=0
  charPromo(char,v)=0
 Next
 charAttacker(char)=0
 charWitness(char)=0
 charFollowTim(char)=0
 charBribeTim(char)=0
 ;risk dead status
 randy=Rnd(0,20)
 If randy=0 And char<>gamChar(slot) Then charLocation(char)=0 : charHealth(char)=Rnd(0,1) 
End Function

;GENERATE WEAPON
Function GenerateWeapon(cyc,style,area,x#,y#,z#)
 ;type
 weapType(cyc)=style
 If weapType(cyc)=0
  weapType(cyc)=Rnd(1,weapList)
  randy=Rnd(1,20)
  If randy=1 Then weapType(cyc)=Rnd(24,25)
  If randy=2 Then weapType(cyc)=15
  If randy=>3 And randy=<5 Then weapType(cyc)=16
  If randy=>6 And randy=<8 Then weapType(cyc)=Rnd(16,18)
 EndIf
 ;general location
 weapLocation(cyc)=area
 If area=0
  randy=Rnd(0,20)
  If randy=<1 Then weapLocation(cyc)=1
  If randy=>2 And randy=<4 Then weapLocation(cyc)=2
  If randy=>4 And randy=<5 Then weapLocation(cyc)=3
  If randy=6 Then weapLocation(cyc)=4
  If randy=>7 And randy=<8 Then weapLocation(cyc)=5
  If randy=9 Then weapLocation(cyc)=6
  If randy=>10 And randy=<11 Then weapLocation(cyc)=7
  If randy=>12 And randy=<13 Then weapLocation(cyc)=8
  If randy=>14 And randy=<16 Then weapLocation(cyc)=9
  If randy=17 Then weapLocation(cyc)=10
  If randy=>18 And randy=<20 Then weapLocation(cyc)=11
 EndIf
 randy=Rnd(0,4)
 If randy=0 And style=0 And area=0 Then weapLocation(cyc)=0
 ;favour habitat
 If weapLocation(cyc)>0 And GetBlock(weapLocation(cyc))=0 And weapType(cyc)<>16
  randy=Rnd(0,2)
  If randy>0 And weapHabitat(weapType(cyc))>0 And weapHabitat(weapType(cyc))<>99 Then weapLocation(cyc)=weapHabitat(weapType(cyc))
  If randy>0 And weapType(cyc)=14 Then weapLocation(cyc)=2
  If randy=0 And weapType(cyc)=>24 And weapType(cyc)=<25 Then weapLocation(cyc)=11
 EndIf
 ;pinpoint location
 weapX#(cyc)=x# : weapY#(cyc)=y# : weapZ#(cyc)=z#
 weapA#(cyc)=Rnd(0,360)
 If weapX#(cyc)=0 And weapY#(cyc)=0 And weapZ#(cyc)=0
  weapY#(cyc)=50 
  ;cell block locations
  If GetBlock(weapLocation(cyc))>0
   randy=Rnd(0,9)
   If randy=<5
    Repeat
     weapX#(cyc)=Rnd(-300,300) : weapZ#(cyc)=Rnd(-140,350)
     If randy=0 Then weapY#(cyc)=50 Else weapY#(cyc)=150
    Until InsideCell(weapX#(cyc),weapY#(cyc),weapZ#(cyc))>0
   EndIf
   If randy=6 Then weapX#(cyc)=Rnd(-190,60) : weapZ#(cyc)=Rnd(-140,250)
   If randy=7 Then weapX#(cyc)=Rnd(60,190) : weapZ#(cyc)=Rnd(-140,250)
   If randy=8 Then weapX#(cyc)=Rnd(-115,115) : weapZ#(cyc)=Rnd(-335,15)
   If randy=9 Then weapX#(cyc)=Rnd(-80,80) : weapZ#(cyc)=Rnd(220,350) : weapY#(cyc)=150
  EndIf
  ;yard locations
  If weapLocation(cyc)=2
   randy=Rnd(1,2)
   If randy=1 Then weapX#(cyc)=Rnd(-20,475) : weapZ#(cyc)=Rnd(210,475)
   If randy=2 Then weapX#(cyc)=Rnd(210,475) : weapZ#(cyc)=Rnd(-50,475)
   If weapType(cyc)=11 Then weapX#(cyc)=Rnd(210,475) : weapZ#(cyc)=Rnd(-50,200)
   If weapType(cyc)=14 Then weapX#(cyc)=Rnd(-30,100) : weapZ#(cyc)=Rnd(270,425)
  EndIf
  ;study locations
  If weapLocation(cyc)=4
   randy=Rnd(1,5)
   If randy=1 Then weapX#(cyc)=Rnd(-135,135) : weapZ#(cyc)=Rnd(-130,-40)
   If randy=2 Then weapX#(cyc)=Rnd(-120,135) : weapZ#(cyc)=Rnd(40,120)
   If randy=3 Then weapX#(cyc)=Rnd(-120,-40) : weapZ#(cyc)=Rnd(-135,120)
   If randy=4 Then weapX#(cyc)=Rnd(40,135) : weapZ#(cyc)=Rnd(-125,105)
   If randy=5 Then weapX#(cyc)=Rnd(-140,140) : weapZ#(cyc)=Rnd(-140,140)
  EndIf
  ;hospital locations
  If weapLocation(cyc)=6 Then weapX#(cyc)=Rnd(-140,140) : weapZ#(cyc)=Rnd(-140,140)
  ;kitchen locations
  If weapLocation(cyc)=8
   randy=Rnd(1,4)
   If randy=1 Then weapX#(cyc)=Rnd(-105,105) : weapZ#(cyc)=Rnd(-325,-160)
   If randy=2 Then weapX#(cyc)=Rnd(-105,250) : weapZ#(cyc)=Rnd(-160,250)
   If randy=3 Then weapX#(cyc)=Rnd(-250,250) : weapZ#(cyc)=Rnd(170,325)
   If randy=4 Then weapX#(cyc)=Rnd(-240,-145) : weapZ#(cyc)=Rnd(-120,140)
  EndIf
  ;hall locations
  If weapLocation(cyc)=9
   weapX#(cyc)=Rnd(-295,295) : weapZ#(cyc)=Rnd(-295,295)
   ;If weapType(cyc)=5 Then weapX#(cyc)=Rnd(-45,55) : weapZ#(cyc)=Rnd(220,295)
  EndIf
  ;workshop locations
  If weapLocation(cyc)=10
   randy=Rnd(1,4)
   If randy=1 Then weapX#(cyc)=Rnd(-95,95) : weapZ#(cyc)=Rnd(-115,115)
   If randy=2 Then weapX#(cyc)=Rnd(-65,-30) : weapZ#(cyc)=-114 : weapY#(cyc)=Rnd(20,35)
   If randy=3 Then weapX#(cyc)=Rnd(30,70) : weapZ#(cyc)=-114 : weapY#(cyc)=Rnd(20,35)
   If randy=4 Then weapX#(cyc)=Rnd(-20,20) : weapZ#(cyc)=119 : weapY#(cyc)=Rnd(20,35)
  EndIf
  ;toilet locations
  If weapLocation(cyc)=11
   randy=Rnd(1,7)
   If randy=>1 And randy=<2 Then weapX#(cyc)=Rnd(-140,50) : weapZ#(cyc)=Rnd(-65,10)
   If randy=>3 And randy=<4 Then weapX#(cyc)=Rnd(50,140) : weapZ#(cyc)=Rnd(-65,70)
   If randy=5 Then weapX#(cyc)=Rnd(-140,-115) : weapZ#(cyc)=Rnd(10,70)
   If randy=6 Then weapX#(cyc)=Rnd(-70,-40) : weapZ#(cyc)=Rnd(10,70)
   If randy=7 Then weapX#(cyc)=Rnd(0,30) : weapZ#(cyc)=Rnd(10,70)
  EndIf
 EndIf
 ;reset status
 weapState(cyc)=1 : weapCarrier(cyc)=0
 weapScar(cyc)=0 : weapOldScar(cyc)=-1
 weapAmmo(cyc)=100 : weapClip(cyc)=10
 If weapStyle(weapType(cyc))=6 Then weapClip(cyc)=0
End Function

;GENERATE NAME
Function GenerateName$(char)
 Repeat
  name$="Character"+Dig$(char,100)
  ;inmates
  If charRole(char)=0
   randy=Rnd(0,1)
   If randy=0 Then name$=textNickName$(Rnd(0,80))
   If randy=1 Then name$=textFirstName$(Rnd(0,65))+" "+textSurName$(Rnd(0,65))
  EndIf
  ;officials
  If charRole(char)=>1
   If charRole(char)=1 Then name$="Warden " 
   If charRole(char)=2 Then name$="Lawyer " 
   If charRole(char)=3 Then name$="Judge " 
   name$=name$+textSurName$(Rnd(0,65))
  EndIf
  ;find conflicts
  conflict=0
  For v=1 To no_chars
   If charName$(v)=name$ Then conflict=1
  Next
 Until conflict=0
 Return name$
End Function

;ASSIGN CELL
Function AssignCell(char)
 its=0
 Repeat
  its=its+1 : satisfied=1
  block=Rnd(1,4) : cell=Rnd(1,20)
  If its<10 And CellPopulation(block,cell)>0 Then satisfied=0
  If CellPopulation(block,cell)>1 Then satisfied=0
  If its<10 And AreaPopulation(TranslateBlock(block),0)=>optPopulation/5 Then satisfied=0
 Until satisfied=1
 charBlock(char)=block
 charCell(char)=cell
End Function

;FIND CELL MATES
Function FindCellMates()
 char=gamChar(slot)
 For v=1 To no_chars
  If v<>char And charRole(v)=0 And charCell(v)=charCell(char) And charBlock(v)=charBlock(char)
   randy=Rnd(0,2)
   If randy=1 Or (randy=0 And charReputation(v)<charReputation(char)) Then charPromo(v,char)=Rnd(202,203)
   If randy=2 Or (randy=0 And charReputation(v)=>charReputation(char)) Then charPromo(v,char)=Rnd(203,204)
  EndIf
 Next
End Function