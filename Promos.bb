;//////////////////////////////////////////////////////////////////////////////
;---------------------------- HARD TIME: PROMOS -------------------------------
;//////////////////////////////////////////////////////////////////////////////

;--------------------------------------------------------------------
;///////////////////// RISK SPONTANEOUS PROMO ///////////////////////
;--------------------------------------------------------------------
Function RiskPromo(cyc,v)
 ;expand range when seated
 talkRange=60
 If pSeat(cyc)>0 Then talkRange=100
 ;//////////////////// LAW ENFORCEMENT ////////////////////
 If charPromo(pChar(cyc),pChar(v))=0 And pChar(v)=gamChar(slot) And charRole(pChar(v))=0 And charRole(pChar(cyc))=1 And gamBlackout(slot)=0 And AttackViable(cyc)=>1 And AttackViable(cyc)=<2 And pDazed(cyc)=0
  ;welcome to location
  If charExperience(pChar(v))=<2 And gamWarrant(slot)=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If gamLocation(slot)=2 And promoUsed(210)=0 Then charPromo(pChar(cyc),pChar(v))=210
   If gamLocation(slot)=4 And promoUsed(211)=0 Then charPromo(pChar(cyc),pChar(v))=211
   If gamLocation(slot)=6 And promoUsed(212)=0 Then charPromo(pChar(cyc),pChar(v))=212
   If gamLocation(slot)=8 And promoUsed(213)=0 Then charPromo(pChar(cyc),pChar(v))=213
   If gamLocation(slot)=10 And promoUsed(215)=0 Then charPromo(pChar(cyc),pChar(v))=215
  EndIf
  ;remind about dinner time
  If gamHours(slot)=13 And gamMins(slot)>20 And gamLocation(slot)<>8 And InProximity(cyc,v,30) And charAngerTim(pChar(cyc),pChar(v))=0 And pAnim(v)<>12 And pAnim(v)<>13
   If promoUsed(6)=0 Then charPromo(pChar(cyc),pChar(v))=6
  EndIf
  ;out of block during lock down
  If LockDown() And GetBlock(gamLocation(slot))<>charBlock(pChar(v)) And InProximity(cyc,v,50)
   If charBribeTim(pChar(cyc))=0 And promoUsed(2)=0 Then charPromo(pChar(cyc),pChar(v))=2
  EndIf
  ;out of cell during lock down
  If LockDown() And GetBlock(gamLocation(slot))=charBlock(pChar(v)) And InsideCell(pX#(v),pY#(v),pZ#(v))<>charCell(pChar(v)) And InProximity(cyc,v,50) And pAnim(v)<>12 And pAnim(v)<>13
   If charBribeTim(pChar(cyc))=0 And promoUsed(3)=0 Then charPromo(pChar(cyc),pChar(v))=3
  EndIf
  ;told off for sleeping in
  If LockDown()=0 And pAnim(v)=103 And gamLocation(slot)<>6 And InProximity(cyc,v,100) And CellVisible(pX#(cyc),pY#(cyc),pZ#(cyc),InsideCell(pX#(v),pY#(v),pZ#(v)))
   If charBribeTim(pChar(cyc))=0 And promoUsed(11)=0 Then charPromo(pChar(cyc),pChar(v))=11
  EndIf
  ;told off for being in foreign cell
  If GetBlock(gamLocation(slot))>0 And InsideCell(pX#(v),pY#(v),pZ#(v))>0 And InProximity(cyc,v,100) And CellVisible(pX#(cyc),pY#(cyc),pZ#(cyc),InsideCell(pX#(v),pY#(v),pZ#(v)))
   If InsideCell(pX#(v),pY#(v),pZ#(v))<>charCell(pChar(v)) Or GetBlock(gamLocation(slot))<>charBlock(pChar(v))
    If charBribeTim(pChar(cyc))=0 And promoUsed(12)=0 Then charPromo(pChar(cyc),pChar(v))=12
   EndIf
  EndIf
  ;caught carrying weapon
  If pWeapon(v)>0 And weapHabitat(weapType(pWeapon(v)))<99 And weapHabitat(weapType(pWeapon(v)))<>gamLocation(slot) And InProximity(cyc,v,weapSize#(weapType(pWeapon(v)))*5) And charBribeTim(pChar(cyc))=0
   If InLine(cyc,p(v),talkRange)
    If gamLocation(slot)=10 And weapCreate(weapType(pWeapon(v)))>0 And pWeapon(v)<>charWeapon(pChar(v)) And promoUsed(53)=0
     charPromo(pChar(cyc),pChar(v))=53
    Else
     If weapHabitat(weapType(pWeapon(v)))=0 And promoUsed(1)=0 Then charPromo(pChar(cyc),pChar(v))=1
     If weapHabitat(weapType(pWeapon(v)))>0 And promoUsed(18)=0 Then charPromo(pChar(cyc),pChar(v))=18
    EndIf
   EndIf
  EndIf
  ;confront about gang membership
  randy=Rnd(0,10000)
  If randy=0 And charGang(pChar(v))>0 And InProximity(cyc,v,30) And charBribeTim(pChar(cyc))=0
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=51
  EndIf
  ;offers/threats
  randy=Rnd(0,10000)
  If gamWarrant(slot)=0 And gamMoney(slot)>0 And charBribeTim(pChar(cyc))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If randy=0 And Friendly(cyc,v)=0 Then charPromo(pChar(cyc),pChar(v))=58 ;invent charge
    If charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0
     If randy=1 Then charPromo(pChar(cyc),pChar(v))=73 ;offer immunity
     If randy=2 Then charPromo(pChar(cyc),pChar(v))=74 ;offer protection
     If randy=3 And charSentence(pChar(v))>0 Then charPromo(pChar(cyc),pChar(v))=245 ;offer day off
    EndIf
   EndIf
  EndIf
  ;appeals to your intelligence
  randy=Rnd(0,10000)
  If randy=0 And gamWarrant(slot)=0 And charIntelligence(pChar(v))>charIntelligence(pChar(cyc)) And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=244
  EndIf
  ;praised for working
  If (pAnim(v)=102 And (pState(v)=102 Or pState(v)=104 Or pState(v)=108)) Or pAnim(v)=92
   randy=Rnd(0,10000)
   If randy=0 And gamWarrant(slot)=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30) 
    If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=246
   EndIf
  EndIf
  ;working is futile
  If (pAnim(v)=102 And pState(v)=104) Or pAnim(v)=92
   If LockDown() And gamWarrant(slot)=0 And InProximity(cyc,v,50) 
    If InLine(cyc,p(v),talkRange) And promoUsed(248)=0 Then charPromo(pChar(cyc),pChar(v))=248
   EndIf
  EndIf
  ;cell change
  randy=Rnd(0,10000)
  If randy=0 And gamWarrant(slot)=0 And charBribeTim(pChar(cyc))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=205
  EndIf 
  ;told to clean scars
  chance=(20-CountScars(v))*100
  If chance<500 Then chance=500
  randy=Rnd(0,chance)
  If randy=0 And CountScars(v)>2 And gamLocation(slot)<>11 And gamWarrant(slot)=0 And charBribeTim(pChar(cyc))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=257
  EndIf
  ;give yourself in
  randy=Rnd(0,100)
  If randy=0 And gamWarrant(slot)>0 And ChannelPlaying(chAlarm) And Friendly(cyc,v)=0 And charBribeTim(pChar(cyc))=0 And InProximity(cyc,v,50)
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=59 
  EndIf
  ;assign mission
  If gamWarrant(slot)=0 And gamMission(slot)=0 And charSentence(pChar(v))>0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    randy=Rnd(0,10000)
    If randy=1 And charStrength(pChar(v))=<80 Then charPromo(pChar(cyc),pChar(v))=141 ;acquire strength
    If randy=2 And charAgility(pChar(v))=<80 Then charPromo(pChar(cyc),pChar(v))=142 ;acquire agility
    If randy=3 And charIntelligence(pChar(v))=<80 Then charPromo(pChar(cyc),pChar(v))=143 ;acquire intelligence
    If randy=5 And charReputation(pChar(v))=>70 Then charPromo(pChar(cyc),pChar(v))=145 ;reduce reputation
    If randy=7 And gamMoney(slot)=>0 And gamMoney(slot)=<1000 Then charPromo(pChar(cyc),pChar(v))=147 ;acquire money
    If randy=8 And charHairStyle(pChar(v))>1 And charHairStyle(pChar(v))<>charHairStyle(pChar(cyc)) Then charPromo(pChar(cyc),pChar(v))=148 ;change hairstyle
    If randy=9 And charCostume(pChar(v))<>charCostume(pChar(cyc)) Then charPromo(pChar(cyc),pChar(v))=149 ;change costume
    If randy=<10 And gamMoney(slot)<0 Then charPromo(pChar(cyc),pChar(v))=146 ;get out of debt
   EndIf
  EndIf
  ;mission reminder
  If gamWarrant(slot)=0 And gamMission(slot)>0 And pChar(cyc)=gamClient(slot) And InProximity(cyc,v,30) 
   If InLine(cyc,p(v),talkRange) And promoUsed(171)=0 Then charPromo(pChar(cyc),pChar(v))=171
  EndIf
  ;give up illegitimate mission
  randy=Rnd(0,10000)
  If randy=0 And gamMission(slot)>0 And charRole(gamClient(slot))=0 And InProximity(cyc,v,30) And charBribeTim(pChar(cyc))=0 
   If InLine(cyc,p(v),talkRange) And promoUsed(179)=0 Then charPromo(pChar(cyc),pChar(v))=179
  EndIf
  ;served sentence
  If charSentence(pChar(v))=<0 And gamWarrant(slot)=0 And InProximity(cyc,v,30)
   If charStrength(pChar(v))=>70 And charAgility(pChar(v))=>70 And charIntelligence(pChar(v))=>70 And gamMoney(slot)=>1000
    If promoUsed(60)=0 Then charPromo(pChar(cyc),pChar(v))=60 ;asked to leave
   Else
    If promoUsed(262)=0 And promoUsed(263)=0
     If gamMoney(slot)<1000 Then charPromo(pChar(cyc),pChar(v))=263 ;not rich enough to leave
     If charStrength(pChar(v))<70 Or charAgility(pChar(v))<70 Or charIntelligence(pChar(v))<70 Then charPromo(pChar(cyc),pChar(v))=262 ;not fit to leave
    EndIf
   EndIf
  EndIf
  ;death sentence!
  If charSentence(pChar(v))=>100 And gamWarrant(slot)=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If promoUsed(247)=0 Then charPromo(pChar(cyc),pChar(v))=247
  EndIf
 EndIf
 ;//////////////////// FELLOW INMATES ////////////////////
 If charPromo(pChar(cyc),pChar(v))=0 And pChar(v)=gamChar(slot) And charRole(pChar(cyc))=0 And charFollowTim(pChar(cyc))=0 And (gamMission(slot)<>18 Or pChar(cyc)<>gamClient(slot)) And AttackViable(cyc)=>1 And AttackViable(cyc)=<2 And pDazed(cyc)=0
  ;welcome to location
  If charExperience(pChar(v))=<2 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If GetRace(pChar(v))=GetRace(pChar(cyc)) Or charGang(pChar(cyc))=0 Or charGang(pChar(cyc))=>4
    If gamLocation(slot)=9 And promoUsed(214)=0 Then charPromo(pChar(cyc),pChar(v))=214
    If gamLocation(slot)=11 And promoUsed(216)=0 Then charPromo(pChar(cyc),pChar(v))=216
   EndIf
  EndIf
  ;introductions
  If charExperience(pChar(v))=<2 And charSnapped(pChar(cyc))=0 And charRelation(pChar(cyc),pChar(v))=0 And charAngerTim(pChar(cyc),pChar(v))=0 And charGang(pChar(cyc))<>charGang(pChar(v)) And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If GetRace(pChar(v))=GetRace(pChar(cyc)) Or charGang(pChar(cyc))=0 Or charGang(pChar(cyc))=>4
     randy=InferiorDice(cyc,v)
     If randy=<1 Then charPromo(pChar(cyc),pChar(v))=98 ;friendly
    EndIf
    If charGang(pChar(cyc))<>6
     randy=SuperiorDice(cyc,v)
     If randy=<1 Then charPromo(pChar(cyc),pChar(v))=99 ;neutral
     If randy=2 Then charPromo(pChar(cyc),pChar(v))=100 ;hostile
    EndIf
   EndIf
  EndIf
  ;their imminent release
  If charSentence(pChar(cyc))=<1 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If Friendly(cyc,v) And promoUsed(221)=0 Then charPromo(pChar(cyc),pChar(v))=221
    If charRelation(pChar(cyc),pChar(v))<0 And promoUsed(222)=0 Then charPromo(pChar(cyc),pChar(v))=222  
   EndIf
  EndIf
  ;your imminent release
  If charSentence(pChar(v))=<1 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If InferiorDice(cyc,v)=<5 And Friendly(cyc,v) And promoUsed(223)=0 Then charPromo(pChar(cyc),pChar(v))=223
    If SuperiorDice(cyc,v)=<5 And charRelation(pChar(cyc),pChar(v))<0 And promoUsed(224)=0 Then charPromo(pChar(cyc),pChar(v))=224  
   EndIf
  EndIf
  ;pestered for seat
  randy=SuperiorDice(cyc,v)
  If pSeat(v)>0 And pSeat(cyc)=0 And Friendly(cyc,v)=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If randy=<1 Or (randy=2 And gamLocation(slot)=8 And trayState(pSeat(cyc))>0) Then charPromo(pChar(cyc),pChar(v))=7
   EndIf
  EndIf 
  ;pestered for bed
  randy=SuperiorDice(cyc,v)
  If pBed(v)>0 And pBed(cyc)=0 And Friendly(cyc,v)=0 And InProximity(cyc,v,50)
   If InLine(cyc,p(v),talkRange)
    If randy=0 Or (randy=1 And LockDown()) Or (randy=2 And gamLocation(slot)=6) Then charPromo(pChar(cyc),pChar(v))=8
   EndIf
  EndIf  
  ;told off for snooping around cell
  cell=InsideCell(pX#(v),pY#(v),pZ#(v))
  If GetBlock(gamLocation(slot))=charBlock(pChar(cyc)) And cell=charCell(pChar(cyc)) And Friendly(cyc,v)=0 And InProximity(cyc,v,100) And CellVisible(pX#(cyc),pY#(cyc),pZ#(cyc),cell) And gamBlackout(slot)=0
   If InsideCell(pX#(cyc),pY#(cyc),pZ#(cyc))=0 Or LockDown() Or pBed(v)=charCell(pChar(cyc))
    If cell<>charCell(pChar(v)) Or GetBlock(gamLocation(slot))<>charBlock(pChar(v))
     If InLine(cyc,p(v),talkRange) And promoUsed(13)=0 Then charPromo(pChar(cyc),pChar(v))=13
    EndIf
   EndIf
  EndIf
  ;caught in friend's cell
  If GetBlock(gamLocation(slot))>0 And cell>0 And (cell<>charCell(pChar(v)) Or GetBlock(gamLocation(slot))<>charBlock(pChar(v))) And Friendly(cyc,v)=0 And InProximity(cyc,v,100) And gamBlackout(slot)=0
   For char=1 To no_chars
    If char<>pChar(cyc) And char<>pChar(v) And charRole(char)=0 And FriendlyChars(pChar(cyc),char) And charPromo(pChar(cyc),pChar(v))=0
     If GetBlock(gamLocation(slot))=charBlock(char) And cell=charCell(char) And CellVisible(pX#(cyc),pY#(cyc),pZ#(cyc),cell)
      If InLine(cyc,p(v),talkRange) And promoUsed(91)=0 Then charPromo(pChar(cyc),pChar(v))=91 : charPromoRef(pChar(cyc))=char
     EndIf
    EndIf
   Next
  EndIf
  ;interest in your item
  If pWeapon(v)>0 And InProximity(cyc,v,30) And gamBlackout(slot)=0
   If InLine(cyc,p(v),talkRange)
    randy=InferiorDice(cyc,v)
    If randy=<1 Or (randy=2 And pAgenda(cyc)=4 And pWeapon(v)=pWeapFoc(cyc)) Or (randy=3 And weapType(pWeapon(cyc))=>16 And weapType(pWeapon(cyc))=<18)
     If charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 Then charPromo(pChar(cyc),pChar(v))=48 ;asks to buy
    EndIf
    randy=SuperiorDice(cyc,v)
    If randy=0 Or (randy=1 And pAgenda(cyc)=4 And pWeapon(v)=pWeapFoc(cyc))
     If Friendly(cyc,v)=0 Then charPromo(pChar(cyc),pChar(v))=16 ;bullied
    EndIf
   EndIf
  EndIf 
  ;has item to sell
  randy=InferiorDice(cyc,v)
  If pWeapon(cyc)>0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If randy=<1 Or (randy=2 And weapType(pWeapon(cyc))=>16 And weapType(pWeapon(cyc))=<18)
     If gamMoney(slot)=>weapValue(weapType(pWeapon(cyc))) Then charPromo(pChar(cyc),pChar(v))=49 ;offers to sell
    EndIf
    If randy=3 Or (randy=4 And charRelation(pChar(cyc),pChar(v))=1) Then charPromo(pChar(cyc),pChar(v))=50 ;offers to give
   EndIf
  EndIf
  ;alarmed by dangerous weapon
  danger=0 : w=weapType(pWeapon(v))
  If w=6 Or w=12 Or (w=>22 And w=<23) Then danger=1
  If w=>7 And w=<9 Then danger=3 
  randy=InferiorDice(cyc,v)
  If randy=<danger And danger>0 And pWeapon(v)>0 And weapHabitat(weapType(pWeapon(v)))<>gamLocation(slot) And InProximity(cyc,v,weapSize#(weapType(pWeapon(v)))*5) And Friendly(cyc,v)=0
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=209
  EndIf
  ;insults
  If Friendly(cyc,v)=0 And charAngerTim(pChar(cyc),pChar(v))=0 And charGang(pChar(cyc))<>6 And InProximity(cyc,v,30) 
   If InLine(cyc,p(v),talkRange)
    If SuperiorDice(cyc,v)=0 And charStrength(pChar(v))<charStrength(pChar(cyc))-10 Then charPromo(pChar(cyc),pChar(v))=226 ;insult strength
    If SuperiorDice(cyc,v)=0 And charAgility(pChar(v))<charAgility(pChar(cyc))-10 Then charPromo(pChar(cyc),pChar(v))=228 ;insult agility
    If SuperiorDice(cyc,v)=0 And charIntelligence(pChar(v))<charIntelligence(pChar(cyc))-10 Then charPromo(pChar(cyc),pChar(v))=230 ;insult intelligence
    If SuperiorDice(cyc,v)=0 And charReputation(pChar(v))<charReputation(pChar(cyc))-10 Then charPromo(pChar(cyc),pChar(v))=232 ;insult reputation
    If SuperiorDice(cyc,v)=0 And gamMoney(slot)>0 And gamMoney(slot)<100 Then charPromo(pChar(cyc),pChar(v))=234 ;insult finances
    If SuperiorDice(cyc,v)=0 And charModel(pChar(v))=>4 And charModel(pChar(cyc))=<3 Then charPromo(pChar(cyc),pChar(v))=236 ;too fat
    If SuperiorDice(cyc,v)=0 And charModel(pChar(v))=1 And charModel(pChar(cyc))=>2 Then charPromo(pChar(cyc),pChar(v))=237 ;too skinny
    If SuperiorDice(cyc,v)=0 And charCrime(pChar(v))<charCrime(pChar(cyc)) Then charPromo(pChar(cyc),pChar(v))=238 ;inferior crime
    If InferiorDice(cyc,v)=0 And charCrime(pChar(v))>charCrime(pChar(cyc))+2 Then charPromo(pChar(cyc),pChar(v))=239 ;superior crime
    If GetBlock(gamLocation(slot))>0 And GetBlock(gamLocation(slot))=charBlock(pChar(cyc)) And GetBlock(gamLocation(slot))<>charBlock(pChar(v))
     If SuperiorDice(cyc,v)=0 Then charPromo(pChar(cyc),pChar(v))=242 ;block rivalry
    EndIf
    If InferiorDice(cyc,v)=0 And charReputation(pChar(v))>charReputation(pChar(cyc)) And charReputation(pChar(v))>70
     charPromo(pChar(cyc),pChar(v))=260 ;challenge reputation
    EndIf
    If SuperiorDice(cyc,v)=0 And GetRace(pChar(v))<>GetRace(pChar(cyc)) Then charPromo(pChar(cyc),pChar(v))=266 ;racial tension
   EndIf
  EndIf
  ;praise
  If charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If InferiorDice(cyc,v)=0 Then charPromo(pChar(cyc),pChar(v))=240 ;comforted about crime
    If InferiorDice(cyc,v)=0 And charStrength(pChar(v))>charStrength(pChar(cyc))+10 Then charPromo(pChar(cyc),pChar(v))=227 ;praise strength
    If InferiorDice(cyc,v)=0 And charAgility(pChar(v))>charAgility(pChar(cyc))+10 Then charPromo(pChar(cyc),pChar(v))=229 ;praise agility
    If InferiorDice(cyc,v)=0 And charIntelligence(pChar(v))>charIntelligence(pChar(cyc))+10 Then charPromo(pChar(cyc),pChar(v))=231 ;praise intelligence
    If InferiorDice(cyc,v)=0 And charReputation(pChar(v))>charReputation(pChar(cyc))+10 Then charPromo(pChar(cyc),pChar(v))=233 ;praise reputation
    If InferiorDice(cyc,v)=0 And gamMoney(slot)>1000 Then charPromo(pChar(cyc),pChar(v))=235 ;praise finances
    If charSnapped(pChar(cyc))=0 And charRelation(pChar(cyc),pChar(v))=0
     If InferiorDice(cyc,v)=0 And GetBlock(gamLocation(slot))=0 And charBlock(pChar(cyc))=charBlock(pChar(v)) Then charPromo(pChar(cyc),pChar(v))=243 ;block comradery
     If InferiorDice(cyc,v)=0 And GetRace(pChar(v))=GetRace(pChar(cyc)) Then charPromo(pChar(cyc),pChar(v))=267 ;racial comradery
    EndIf
   EndIf
  EndIf
  ;ridiculed for working
  If (pAnim(v)=102 And pState(v)=104) Or pAnim(v)=92
   randy=SuperiorDice(cyc,v)
   If randy=0 And Friendly(cyc,v)=0 And charGang(pChar(cyc))<>6 And InProximity(cyc,v,50) 
    If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=241
   EndIf
  EndIf
  ;too close for comfort
  If Friendly(cyc,v)=0 And charAngerTim(pChar(cyc),pChar(v))=0 And charGang(pChar(cyc))<>6 And InProximity(cyc,v,30)
   randy=SuperiorDice(cyc,v)
   If randy=<1 And pAnim(cyc)=>12 And pAnim(cyc)=<13 And pAnim(v)<20 And pAgenda(cyc)<>2 And InProximity(cyc,v,15)
    If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=254 ;get out of the way
   EndIf
   If randy=>2 And randy=<3 And pAnim(cyc)=>12 And pAnim(cyc)=<13 And pAnim(v)=>12 And pAnim(v)=<13
    If InLine(v,p(cyc),talkRange) And InLine(cyc,p(v),talkRange)=0 Then charPromo(pChar(cyc),pChar(v))=255 ;following me?!
   EndIf
   If randy=>4 And randy=<5 And pFoc(cyc)=v And pFoc(v)=cyc And pAnim(cyc)=0 And pAnim(v)=0 And pDazed(v)=0
    If InLine(cyc,p(v),talkRange) And InLine(v,p(cyc),talkRange) Then charPromo(pChar(cyc),pChar(v))=256 ;staring at me?!
   EndIf
  EndIf 
  ;gang membership
  chance=10000
  If charGang(pChar(v))=0 Then chance=chance/2 
  If charRelation(pChar(cyc),pChar(v))>0 Then chance=chance/2
  randy=Rnd(0,chance) 
  If charGang(pChar(cyc))>0 And charGang(pChar(v))<>charGang(pChar(cyc)) And charGangHistory(pChar(v),charGang(pChar(cyc)))=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) And (GetRace(pChar(v))=GetRace(pChar(cyc)) Or charGang(pChar(cyc))=>4)
    If randy=<1 And charGang(pChar(cyc))=>1 And charGang(pChar(cyc))=<3
     If charReputation(pChar(v))=>70 Then charPromo(pChar(cyc),pChar(v))=32+charGang(pChar(cyc)) Else charPromo(pChar(cyc),pChar(v))=94
    EndIf
    If randy=<1 And charGang(pChar(cyc))=4
     If charIntelligence(pChar(v))=>70 Then charPromo(pChar(cyc),pChar(v))=36 Else charPromo(pChar(cyc),pChar(v))=94
    EndIf
    If randy=<1 And charGang(pChar(cyc))=5
     If charStrength(pChar(v))+charAgility(pChar(v))=>140 Then charPromo(pChar(cyc),pChar(v))=37 Else charPromo(pChar(cyc),pChar(v))=94
    EndIf
    If randy=<1 And charGang(pChar(cyc))=6
     If charReputation(pChar(v))<70 Then charPromo(pChar(cyc),pChar(v))=38 Else charPromo(pChar(cyc),pChar(v))=94
    EndIf
    If randy=2 And gamMoney(slot)>100 Then charPromo(pChar(cyc),pChar(v))=93 ;buy in
   EndIf
  EndIf
  ;internal gang issues
  randy=SuperiorDice(cyc,v)
  If charGang(pChar(v))>0 And charGang(pChar(v))=charGang(pChar(cyc)) And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If randy=0 And gamMoney(slot)>100 Then charPromo(pChar(cyc),pChar(v))=95 ;kick up
    If promoUsed(96)=0
     If charGang(pChar(v))=1 And (charHairStyle(pChar(v))>1 Or charSpecs(pChar(v))<>4) Then charPromo(pChar(cyc),pChar(v))=96 ;conform to Suns
     If charGang(pChar(v))=5 And charCostume(pChar(v))>2 Then charPromo(pChar(cyc),pChar(v))=96 ;conform to Gladiators
    EndIf
   EndIf
  EndIf
  ;asks to join your gang
  randy=InferiorDice(cyc,v)
  If randy=0 And charGang(pChar(v))>0 And charGang(pChar(v))<>charGang(pChar(cyc)) And charGangHistory(pChar(cyc),charGang(pChar(v)))=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) And (GetRace(pChar(v))=GetRace(pChar(cyc)) Or charGang(pChar(v))=>4)
    charPromo(pChar(cyc),pChar(v))=92 
   EndIf
  EndIf
  ;gang rivalry
  randy=SuperiorDice(cyc,v)
  If randy=0 And charGang(pChar(cyc))>0 And charGang(pChar(v))>0 And charGang(pChar(v))<>charGang(pChar(cyc)) And charGang(pChar(cyc))<>6 And Friendly(cyc,v)=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=225
  EndIf
  ;asks to bury the hatchet
  randy=InferiorDice(cyc,v)
  If randy=0 And charRelation(pChar(cyc),pChar(v))<0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=97
  EndIf
  ;witness blackmails
  If gamWarrant(slot)>0 And gamMoney(slot)>0 And pChar(cyc)=charWitness(pChar(v)) And Friendly(cyc,v)=0 And InProximity(cyc,v,50)
   If InLine(cyc,p(v),talkRange) And promoUsed(55)=0 Then charPromo(pChar(cyc),pChar(v))=55 
  EndIf
  ;offers to take blame
  randy=InferiorDice(cyc,v)
  If gamWarrant(slot)>0 And gamMoney(slot)>0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If randy=0 Or (randy=1 And Friendly(cyc,v)) Then charPromo(pChar(cyc),pChar(v))=56 
   EndIf
  EndIf
  ;asks you to take blame
  randy=Rnd(0,10000)
  If gamWarrant(slot)=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If randy=0 Or (randy=1 And Friendly(cyc,v)) Then charPromo(pChar(cyc),pChar(v))=57 
   EndIf
  EndIf
  ;offers protection
  randy=SuperiorDice(cyc,v)
  If randy=0 And gamWarrant(slot)=0 And gamMoney(slot)>0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And charFollowTim(pChar(cyc))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=74
  EndIf
  If charFollowTim(pChar(cyc))=>1 And charFollowTim(pChar(cyc))=<100 Then charPromo(pChar(cyc),pChar(v))=76
  ;offers to attack enemy
  randy=SuperiorDice(cyc,v)
  If randy=0 And gamMoney(slot)>0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   charPromoRef(pChar(cyc))=0 : its=0
   Repeat
    charPromoRef(pChar(cyc))=pChar(Rnd(1,no_plays))
    its=its+1
    If its>100 Then charPromoRef(pChar(cyc))=0
   Until charPromoRef(pChar(cyc))=0 Or charRelation(pChar(v),charPromoRef(pChar(cyc)))<0 
   If charPromoRef(pChar(cyc))>0 And InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=81
  EndIf
  ;friend of a friend
  randy=InferiorDice(cyc,v)
  If randy=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   charPromoRef(pChar(cyc))=0 : its=0
   Repeat
    charPromoRef(pChar(cyc))=Rnd(4,no_chars)
    its=its+1
    If its>100 Then charPromoRef(pChar(cyc))=0
   Until charPromoRef(pChar(cyc))=0 Or (charRelation(pChar(cyc),charPromoRef(pChar(cyc)))>0 And charRelation(pChar(v),charPromoRef(pChar(cyc)))>0)
   If charPromoRef(pChar(cyc))>0 And InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=84
  EndIf 
  ;enemy by association
  randy=SuperiorDice(cyc,v)
  If randy=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   charPromoRef(pChar(cyc))=0 : its=0
   Repeat
    charPromoRef(pChar(cyc))=Rnd(4,no_chars)
    its=its+1
    If its>100 Then charPromoRef(pChar(cyc))=0
   Until charPromoRef(pChar(cyc))=0 Or (charRelation(pChar(cyc),charPromoRef(pChar(cyc)))>0 And charRelation(pChar(v),charPromoRef(pChar(cyc)))<0)
   If charPromoRef(pChar(cyc))>0 And InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=85
  EndIf
  ;asked to give up friend
  randy=SuperiorDice(cyc,v)
  If randy=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   charPromoRef(pChar(cyc))=0 : its=0
   Repeat
    charPromoRef(pChar(cyc))=Rnd(4,no_chars)
    its=its+1
    If its>100 Then charPromoRef(pChar(cyc))=0
   Until charPromoRef(pChar(cyc))=0 Or (charRelation(pChar(cyc),charPromoRef(pChar(cyc)))<0 And charRelation(pChar(v),charPromoRef(pChar(cyc)))>0)
   If charPromoRef(pChar(cyc))>0 And InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=86
  EndIf 
  ;assign mission
  If charGang(pChar(v))>0 And charGang(pChar(v))=charGang(pChar(cyc)) Then gang=1 Else gang=0
  If gamMission(slot)=0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
   If InLine(cyc,p(v),talkRange)
    If gang=1 Then randy=Rnd(0,10000) Else randy=Rnd(0,20000)
    If gang=1
     If randy=1 And charStrength(pChar(v))=<80 Then charPromo(pChar(cyc),pChar(v))=141 ;acquire strength
     If randy=2 And charAgility(pChar(v))=<80 Then charPromo(pChar(cyc),pChar(v))=142 ;acquire agility
     If randy=3 And charIntelligence(pChar(v))=<80 Then charPromo(pChar(cyc),pChar(v))=143 ;acquire intelligence
     If randy=4 And charReputation(pChar(v))=<80 And charGang(pChar(v))<>6 Then charPromo(pChar(cyc),pChar(v))=144 ;acquire reputation
     If randy=5 And charReputation(pChar(v))=>70 And charGang(pChar(v))=6 Then charPromo(pChar(cyc),pChar(v))=145 ;reduce reputation
     If randy=7 And gamMoney(slot)=>0 And gamMoney(slot)=<1000 Then charPromo(pChar(cyc),pChar(v))=147 ;acquire money
     If randy=8 And charHairStyle(pChar(v))>1 And charHairStyle(pChar(v))<>charHairStyle(pChar(cyc)) Then charPromo(pChar(cyc),pChar(v))=148 ;change hairstyle
     If randy=9 And charCostume(pChar(v))<>charCostume(pChar(cyc)) Then charPromo(pChar(cyc),pChar(v))=149 ;change costume
     If randy=<9 And gamMoney(slot)<0 Then charPromo(pChar(cyc),pChar(v))=146 ;get out of debt 
     If randy=19 And gamWarrant(slot)=0 And charGang(pChar(v))<>6 Then charPromo(pChar(cyc),pChar(v))=159 ;get arrested
    EndIf    
    If randy=10 Then charPromo(pChar(cyc),pChar(v))=150 ;bring item
    If randy=11 And pWeapon(cyc)>0 Then charPromo(pChar(cyc),pChar(v))=151 ;deliver given item
    If randy=12 Then charPromo(pChar(cyc),pChar(v))=152 ;find & deliver item 
    If randy=13 And charGang(pChar(cyc))<>6 Then charPromo(pChar(cyc),pChar(v))=153 ;kill character
    If randy=14 And charGang(pChar(cyc))<>6 Then charPromo(pChar(cyc),pChar(v))=154 ;injure character
    If randy=15 And charGang(pChar(cyc))<>6 Then charPromo(pChar(cyc),pChar(v))=155 ;assault character
    If randy=16 Then charPromo(pChar(cyc),pChar(v))=156 ;meet character
    If randy=17 Then charPromo(pChar(cyc),pChar(v))=157 ;identify character
    If randy=18 And gamHours(slot)=<20 Then charPromo(pChar(cyc),pChar(v))=158 ;guard character
    If randy=20 And charGang(pChar(v))=0 Then charPromo(pChar(cyc),pChar(v))=160 ;join gang
   EndIf
  EndIf
  ;mission reminder
  If gamMission(slot)>0 And pChar(cyc)=gamClient(slot) And InProximity(cyc,v,50) 
   If InLine(cyc,p(v),talkRange) And promoUsed(171)=0 Then charPromo(pChar(cyc),pChar(v))=171
  EndIf
 EndIf
 ;//////////////////// UNIVERSAL ISSUES ////////////////////
 If charPromo(pChar(cyc),pChar(v))=0 And pChar(v)=gamChar(slot)
  If charFollowTim(pChar(cyc))=0 And (gamMission(slot)<>18 Or pChar(cyc)<>gamClient(slot)) And AttackViable(cyc)=>1 And AttackViable(cyc)=<2 And pDazed(cyc)=0
   ;offers to heal you
   randy=Rnd(0,5000) 
   If gamLocation(slot)=6 Then randy=Rnd(0,1000)
   If randy=0 And gamMoney(slot)>0 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
    injury=0
    For limb=1 To 40
     If pScar(v,limb)=>5 Then injury=injury+1
    Next
    If injury>0 And InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=250
   EndIf
   ;offers to forge qualifications
   randy=Rnd(0,10000) 
   If gamLocation(slot)=4 Then randy=Rnd(0,5000)
   If randy=0 And gamMoney(slot)>100 And charIntelligence(pChar(v))=<75 And charRelation(pChar(cyc),pChar(v))=>0 And charAngerTim(pChar(cyc),pChar(v))=0 And InProximity(cyc,v,30)
    If InLine(cyc,p(v),talkRange) Then charPromo(pChar(cyc),pChar(v))=253
   EndIf
  EndIf
  ;time up on bribes
  If charBribeTim(pChar(cyc))=>1 And charBribeTim(pChar(cyc))=<100 And charFollowTim(pChar(cyc))=0 Then charPromo(pChar(cyc),pChar(v))=75
  If charFollowTim(pChar(cyc))=>1 And charFollowTim(pChar(cyc))=<100 Then charPromo(pChar(cyc),pChar(v))=76
 EndIf
 ;//////////////////// LAST MINUTE LOGIC ////////////////////
 If charPromo(pChar(cyc),pChar(v))>0
  ;no longer carrying weapon
  If charPromo(pChar(cyc),pChar(v))=1 Or charPromo(pChar(cyc),pChar(v))=16 Or charPromo(pChar(cyc),pChar(v))=18 Or charPromo(pChar(cyc),pChar(v))=48 Or charPromo(pChar(cyc),pChar(v))=53 Or charPromo(pChar(cyc),pChar(v))=72
   If pWeapon(v)=0 Then charPromo(pChar(cyc),pChar(v))=0
  EndIf
  If charPromo(pChar(cyc),pChar(v))=49 Or charPromo(pChar(cyc),pChar(v))=50
   If pWeapon(cyc)=0 Then charPromo(pChar(cyc),pChar(v))=0
  EndIf
  ;no longer in a gang
  If charPromo(pChar(cyc),pChar(v))=42 And charGang(pChar(cyc))=0 Then charPromo(pChar(cyc),pChar(v))=0
  If charPromo(pChar(cyc),pChar(v))=47
   If charGang(pChar(cyc))=0 Or charGang(pChar(v))=0 Then charPromo(pChar(cyc),pChar(v))=0
  EndIf
  If charPromo(pChar(cyc),pChar(v))=45 And charGang(pChar(v))=0 Then charPromo(pChar(cyc),pChar(v))=0
  If charPromo(pChar(cyc),pChar(v))=46 And charGang(pChar(cyc))=0 Then charPromo(pChar(cyc),pChar(v))=0
  ;no longer wanted
  If charPromo(pChar(cyc),pChar(v))=55 Or charPromo(pChar(cyc),pChar(v))=56 Or charPromo(pChar(cyc),pChar(v))=59
   If gamWarrant(slot)=0 Then charPromo(pChar(cyc),pChar(v))=0
  EndIf
  ;victim no longer dead
  If charPromo(pChar(cyc),pChar(v))=82 Or charPromo(pChar(cyc),pChar(v))=83
   If charLocation(charPromoRef(pChar(cyc)))>0 Then charPromo(pChar(cyc),pChar(v))=0
  EndIf
  ;life no longer in danger
  If charPromo(pChar(cyc),pChar(v))=258 And pHealth(cyc)=<0 Then charPromo(pChar(cyc),pChar(v))=0
  ;already used
  If promoUsed(charPromo(pChar(cyc),pChar(v)))<>0 Then charPromo(pChar(cyc),pChar(v))=0
 EndIf
End Function

;--------------------------------------------------------------------
;/////////////////////////// PROMO TEXT /////////////////////////////
;--------------------------------------------------------------------
Function DisplayPromo()
 ;translate identities
 cyc=promoActor(1)
 v=promoActor(2)
 oldFoc=camFoc
 ;introduce widescreen
 If gamPromo>0 
  y#=60
  If promoTim=<25 Then y#=PercentOf#(60,promoTim*4) 
  If promoTim=>9975 Then y#=PercentOf#(60,(10000-promoTim)*4) 
  Color 0,0,0 : Rect rX#(0),rY#(0),rX#(800),rY#(y#),1
  y#=480
  If promoTim=<25 Then y#=600-PercentOf#(120,promoTim*4) 
  If promoTim=>9975 Then y#=600-PercentOf#(120,(10000-promoTim)*4) 
  Color 0,0,0 : Rect rX#(0),rY#(y#),rX#(800),rY#(600),1
 EndIf 
 ;determine font
 SetFont font(2)
 If GraphicsWidth()<800 Then SetFont font(3)
 If GraphicsWidth()>800 Then SetFont font(5)
 If GraphicsWidth()>1024 Then SetFont font(6)
 ;1. GUARD CONFRONTS ABOUT ILLEGAL WEAPON
 If gamPromo=1
  ;intro
  optionA$=translate("Yes, drop weapon...") : optionB$=translate("No, it's mine!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, stop where you are! What", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("are you doing with that #FIRST#?", Lower$(weapName$(weapType(pWeapon(v))))),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You know you're not allowed to carry weapons!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Put it down immediately or there'll be trouble..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    charAngerTim(pChar(cyc),pChar(v))=0 : promoEffect=1
   EndIf
   Outline(translate("That's right. Step away from the weapon and"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("perhaps we won't have to take this any further..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    randy=Rnd(0,5)
    If randy=0 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    If randy=1 And gamWarrant(slot)<4 And pWeapon(v)>0 And gamMission(slot)<>11 And gamMission(slot)<>12 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(v)
    promoEffect=1
   EndIf
   Outline(translate("Well, you better know how to use it because"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("i'm gonna kick your ass until you give it up!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;2. TOLD TO RETURN TO HOME BLOCK
 If gamPromo=2
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, didn't you hear the buzzer? This", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("place has been locked down for the night!"),rX#(400),rY#(560),30,30,30,250,250,250) 
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("You're supposed to be in the #FIRST# Block.", textBlock$(charBlock(pChar(v)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Make your way there before i drag your sorry ass!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;3. TOLD TO RETURN TO HOME CELL
 If gamPromo=3
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Come on, #FIRST#, get back to your cell! We're", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("trying to lock this place down for the night..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("Your bed is in Cell #FIRST#. Use that one or", charCell(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("i'll have to put you in a HOSPITAL bed!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;4. TOLD OFF FOR FIGHTING
 If gamPromo=4
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, what's the problem here?", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("If there's any fighting to do, i'll do it!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    charAngerTim(pChar(cyc),pChar(v))=0 : promoEffect=1
   EndIf
   Outline(translate("All you animals have to worry about is the rules,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so stop bickering before i really lose my temper!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;5. TOLD OFF FOR ATTACKING GUARD
 If gamPromo=5
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, you've got no business", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("putting your hands on a police officer!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("If you want to pick a fight with us, we'll make"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("your life even more unbearable inside these walls!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("Now straighten up and fly right before i"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("show you how hard a REAL man can hit!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;6. REMINDED ABOUT DINNER TIME
 If gamPromo=6
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, #FIRST#, didn't you hear the bell? Dinner", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("is served! Go and get something to eat..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;7. TOLD TO GIVE UP SEAT
 If gamPromo=7
  ;intro
  optionA$=translate("Yes, give up seat...") : optionB$=translate("No, go away!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1) : promoVariable=pSeat(v)
   Outline(translate("Hey, #FIRST#, get out of that seat!", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You've been hogging it all day. It's MY turn!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoVariable>0 
    target=FindChild(world,"Chair"+Dig$(promoVariable,10))
    pTX#(cyc)=EntityX(target,1) : pTZ#(cyc)=EntityZ(target,1)
    pAgenda(cyc)=-1 : pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
   EndIf 
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),0)
    promoEffect=1
   EndIf
   Outline(translate("That's right - take your lazy ass somewhere else!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm the king of this place and i deserve a throne..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1 
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    promoEffect=1
   EndIf
   Outline(translate("Fine! If you won't abdicate the throne, i'll"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("just have to drag your sorry ass from it!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;8. TOLD TO GIVE UP BED
 If gamPromo=8
  ;intro
  optionA$=translate("Yes, give up bed...") : optionB$=translate("No, go away!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1) : promoVariable=pBed(v)
   Outline(translate("Hey, #FIRST#, get out of that bed!", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I need to sleep too - and we're not sharing!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoVariable>0 
    target=FindChild(world,"Bed"+Dig$(promoVariable,10))
    pTX#(cyc)=EntityX(target,1) : pTZ#(cyc)=EntityZ(target,1)
    pAgenda(cyc)=-1 : pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
   EndIf 
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),0)
    promoEffect=1
   EndIf
   Outline(translate("That's right - take your lazy ass somewhere else!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("And don't disturb me while i'm trying to sleep..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    promoEffect=1
   EndIf
   Outline(translate("Fine! If you won't stop dreaming, i'll"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("give you a NIGHTMARE to wake up to!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;9. COMPLAIN ABOUT LOSING SEAT
 If gamPromo=9
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    DamageRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    promoEffect=1
   EndIf
   Outline(translate("Hey, #FIRST#, i was sitting there!", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Since i'm on my feet, i should kick your ass!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;10. COMPLAIN ABOUT LOSING BED
 If gamPromo=10
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    DamageRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    promoEffect=1
   EndIf
   Outline(translate("Hey, #FIRST#, i was sleeping there!", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You don't wake me up unless you want a fight!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;11. TOLD TO STOP SLEEPING
 If gamPromo=11
  ;intro
  optionA$=translate("Yes, get up...") : optionB$=translate("No, leave me alone!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, sleeping time is over!", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Get out of bed before i drag you out!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    charAngerTim(pChar(cyc),pChar(v))=0 : promoEffect=1
   EndIf
   Outline(translate("That's right - wake your lazy ass up!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("There's plenty you could be doing..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    randy=Rnd(0,5)
    If randy=0 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    promoEffect=1
   EndIf
   Outline(translate("Fine! If you want to sleep all day, i'll"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("give you a reason to be flat on your back!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;12. GUARD TELLS YOU TO LEAVE FOREIGN CELL
 If gamPromo=12
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
    randy=Rnd(0,10)
    If randy=0 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    promoEffect=1
   EndIf
   Outline(translate("Hey, #FIRST#, get out of that cell!", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You've got no business being in there..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;13. INMATE TELLS YOU TO LEAVE HIS CELL
 If gamPromo=13
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
    promoEffect=1
   EndIf
   Outline(translate("Hey, what are you doing in MY cell?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Get out of there before i kick you out!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;14. COMPLAIN ABOUT UNPROVOKED ATTACK
 If gamPromo=14
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, what's your problem?! Touch me again"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and it'll be the last thing you ever do..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;15. CONFRONT ABOUT ATTACKING FRIEND
 If gamPromo=15
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : ShowPhoto(charPromoRef(pChar(cyc)))
   Outline(translate("Hey, #FIRST#, watch who you mess with!", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("#FIRST# is a personal friend of mine...", charName$(charPromoRef(pChar(cyc)))),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
    promoEffect=1
   EndIf
   Outline(translate("An attack on MY friends is an attack on"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("ME, so let's see how tough you are now!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;16. INMATE DEMANDS ITEM
 If gamPromo=16
  ;intro
  optionA$=translate("Yes, give item...") : optionB$=translate("No, it's mine!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, i need that #SECOND#!", charName$(pChar(v)), Lower$(weapName$(weapType(pWeapon(v))))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Give it to me or i'll take it by force..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Thanks, this should come in handy!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Maybe i'll return the favour some time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=4 : pWeapFoc(cyc)=pWeapon(v)
    promoEffect=1
   EndIf
   Outline(translate("Well, it better be worth it because i'm"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("gonna kick your ass until you give it up!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;17. COMPLAIN ABOUT STOLEN ITEM
 If gamPromo=17
  ;intro
  optionA$=translate("Yes, return item...") : optionB$=translate("No, it's mine!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, that's my #SECOND#!", charName$(pChar(v)), Lower$(weapName$(weapType(pWeapon(v))))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Give it back or i'll show you what it's for..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),0)
    charAngerTim(pChar(cyc),pChar(v))=0
    promoEffect=1
   EndIf
   Outline(translate("I should think so too! If you ever touch my stuff"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("again, i won't give you a choice in the matter..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=4 : pWeapFoc(cyc)=pWeapon(v)
    randy=Rnd(0,5)
    If randy=0 And charRole(pChar(cyc))=1 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    If pWeapon(v)>0 And gamMission(slot)<>11 And gamMission(slot)<>12
     If randy=1 And charRole(pChar(cyc))=1 And gamWarrant(slot)<4 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(v)
     If randy=2 And gamWarrant(slot)<7 Then gamWarrant(slot)=7 : gamItem(slot)=pWeapon(v)
    EndIf
    promoEffect=1
   EndIf
   Outline(translate("Well, it better be worth it because i'm"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("gonna kick your ass until you give it up!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;18. CARRYING ITEM OUT OF CONTEXT
 If gamPromo=18
  ;intro
  optionA$=translate("Yes, drop item...") : optionB$=translate("No, it's mine!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1) : promoVariable=weapType(pWeapon(v))
   Outline(translate("Hey, #FIRST#, stop where you are! What", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("are you doing with that #FIRST#?", Lower$(weapName$(promoVariable))),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You know that kind of thing doesn't belong here!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Put it down immediately or there'll be trouble..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    charAngerTim(pChar(cyc),pChar(v))=0
    promoEffect=1
   EndIf
   Outline(translate("That's right. Step away from the #FIRST#", Lower$(weapName$(promoVariable))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and we won't have to take this any further..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    randy=Rnd(0,5)
    If randy=0 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    If randy=1 And gamWarrant(slot)<4 And pWeapon(v)>0 And gamMission(slot)<>11 And gamMission(slot)<>12 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(v)
    If randy=2 And gamWarrant(slot)<5 And pWeapon(v)>0 And weapType(pWeapon(v))=>16 And weapType(pWeapon(v))=<18 Then gamWarrant(slot)=5
    promoEffect=1
   EndIf
   Outline(translate("Well, that just makes me all the more suspicious!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Anything valuable to you is worth confiscating..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;19. NOT INTELLIGENT ENOUGH TO USE COMPUTER
 If gamPromo=19
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Damn, i wish i was intelligent enough to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("use this computer! It could come in handy..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;20. NOT INTELLIGENT ENOUGH TO COOK
 If gamPromo=20
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Damn, i wish i was intelligent enough to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("prepare food! It's not as tiring as sweeping..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;21. NOT INTELLIGENT ENOUGH TO WORK IN STUDY
 If gamPromo=21
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Damn, i wish i was intelligent enough to arrange"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("these files! It pays better than preparing food..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;22. NOT INTELLIGENT ENOUGH TO WORK IN MEDICINE
 If gamPromo=22
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Damn, i wish i was intelligent enough to mix"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("these chemicals! It pays better than filing..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;23. NOT STRONG ENOUGH TO MAKE WEAPONS
 If gamPromo=23
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Damn, i wish i was strong enough to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("make things! This could come in handy..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;24. LAWYER OFFERS TO REDUCE SENTENCE
 If gamPromo=24
  ;intro
  optionA$=translate("Yes, pay $#FIRST#!", GetFigure$(promoCash)) : optionB$=translate("No, forget it...")
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hi, #FIRST#, it's your lawyer speaking.", charName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I've got some good news about your case!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Some new evidence has cast doubt on your conviction,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so we should be able to get your sentence reduced!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("The only problem is we'll need $#FIRST# to take", GetFigure$(promoCash)),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("it to court. Do you want to wire me the money?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>975 Then camFoc=v
  If promoStage=0 And promoTim>1000 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    PlaySound sCash : statTim(6)=100 : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charSentence(pChar(v))=charSentence(pChar(v))-7
    charHappiness(pChar(v))=charHappiness(pChar(v))+10
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("Alright, i'll get onto it immediately! Your"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("sentence should be down to just #FIRST# days...", Lower$(GetFigure$(charSentence(pChar(v))))),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   Outline(translate("Damn, i thought we were onto something here!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I guess you don't care about getting out..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;25. TANOY ANNOUNCES LOCK DOWN
 If gamPromo=25
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("ATTENTION! The prison is being locked down for the"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("night. All inmates should return to their home cell..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;26. TANOY ANNOUNCES NEW DAY
 If gamPromo=26
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("ATTENTION! Lock down is over. All inmates"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("should wake up and resume their rehabilitation..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;27. TANOY ANNOUNCES DINNER TIME
 If gamPromo=27
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("ATTENTION! Dinner is served in the canteen."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Take your seat now to avoid disappointment..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;28. COURT CASE VICTORY AFTERMATH
 If gamPromo=28
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, #FIRST#, we both know that you", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("deserved to be crucified by that judge!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("There may not be any justice in HIS court"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("room, but there is justice in MY prison!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<1000 Then charAngerTim(pChar(cyc),pChar(v))=1000
    promoEffect=1
   EndIf
   Outline(translate("For every day you should have been sentenced"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("to, i'm gonna make your life a living hell!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;29. COURT CASE FAILURE AFTERMATH
 If gamPromo=29
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, #FIRST#, we both know that", CellName$(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("justice was done in that courtroom!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),0)
    charAngerTim(pChar(cyc),pChar(v))=0
    promoEffect=1
   EndIf
   Outline(translate("There's no reason for either of us to hold a grudge,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so just toe the line and we won't have a problem..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;30. TANOY ANNOUNCES NEW ARRIVAL
 If gamPromo=30
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325 And charRole(gamArrival(slot))=0
   Speak(cyc,2)
   Outline(translate("ATTENTION! A new inmate called '")+charName$(gamArrival(slot))+translate("'"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("will now occupy Cell ")+charCell(gamArrival(slot))+translate(" of the ")+textBlock$(charBlock(gamArrival(slot)))+translate(" Block..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And charRole(gamArrival(slot))=1
   Speak(cyc,2)
   Outline(translate("ATTENTION! A new officer called '")+charName$(gamArrival(slot))+translate("'"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("will now patrol the ")+textLocation$(charLocation(gamArrival(slot)))+translate(" area..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : gamArrival(slot)=0
 EndIf
 ;31. TANOY ANNOUNCES FATALITY
 If gamPromo=31
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<9975
   ShowPhoto(gamFatality(slot))
   For count=1 To no_plays
    If pAnim(count)<20 Then ChangeAnim(count,131)
   Next
  EndIf
  If promoTim>25 And promoTim<325 And charRole(gamFatality(slot))=0
   Speak(cyc,2)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamFatality(slot))+translate(", otherwise known"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("as '")+charName$(gamFatality(slot))+translate("', has been found dead!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And charRole(gamFatality(slot))=1
   Speak(cyc,2)
   Outline(translate("ATTENTION! ")+charName$(gamFatality(slot)),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("has been found dead!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650 And (charAttacker(gamFatality(slot))=0 Or charWitness(charAttacker(gamFatality(slot)))=0)
   Speak(cyc,2)
   Outline(translate("Unfortunately, no witnesses have come forward"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("to shed light on the circumstances of his death..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650 And charAttacker(gamFatality(slot))>0 And charWitness(charAttacker(gamFatality(slot)))>0
   Speak(cyc,2)
   Outline(translate("His untimely death is thought to be related"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("to a dispute with ")+charName$(charAttacker(gamFatality(slot)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975 And charRole(gamFatality(slot))=0
   Speak(cyc,2)
   Outline(translate("Our condolences go out to both his friends within the"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("prison and the family that he leaves behind outside..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975 And charRole(gamFatality(slot))=1
   Speak(cyc,2)
   Outline(translate("We at the prison are deeply saddened by his passing,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and our condolences go to his family on the outside..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : gamFatality(slot)=0
 EndIf
 ;32. TANOY ANNOUNCES RELEASE
 If gamPromo=32
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<9975 Then ShowPhoto(gamRelease(slot))
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamRelease(slot))+translate(", otherwise known"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("as '")+charName$(gamRelease(slot))+translate("', has been released..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("He served his sentence for ")+Lower$(textCrime$(charCrime(gamRelease(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and we now welcome him back into society..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : gamRelease(slot)=0
 EndIf
 ;33. INVITED TO JOIN SUNS OF GOD
 If gamPromo=33
  ;intro
  optionA$=translate("Yes, join gang!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", your skin is white"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("but you're not letting it shine bright!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Why don't you join The Suns Of God and help us"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("purify this place? We could use a guy like you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),1)
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Welcome to the family, brother! Discovering"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("your roots is the first step to growing strong..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("You're either with us or against us! If you're"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("not part of the solution, you're the problem..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;34. INVITED TO JOIN AVATARS OF ALLAH
 If gamPromo=34
  ;intro
  optionA$=translate("Yes, join gang!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", you've been sent to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("this prison to serve a greater cause..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Join The Avatars Of Allah and help us wage war"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("on the infidels! We could use a guy like you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),2)
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Welcome to the family, brother! Surrender your"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("life to the cause and justice will be done..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("You're either with us or against us! If you don't"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("kill the infidels, you'll die alongside them..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;35. INVITED TO JOIN THE DARK SIDE
 If gamPromo=35
  ;intro
  optionA$=translate("Yes, join gang!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", aren't you tired of"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("being judged by the colour of your skin?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("This place is designed to keep the black man down,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("but join The Dark Side and we can fight back!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),3)
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Welcome to the family, brother! Discovering"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("your roots is the first step to growing strong..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("You're either with us or against us! If you're"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("not part of the solution, you're the problem..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;36. INVITED TO JOIN POWERS THAT BE
 If gamPromo=36
  ;intro
  optionA$=translate("Yes, join gang!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", haven't you ever heard"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("that the pen is mightier than the sword?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Knowledge is power, so join The Powers That Be"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and we can bring down this system from within!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),4)
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Welcome to the family, brother! If we put our heads"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("together, we'll have dominion over the animals..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("If you're not intelligent enough to see sense,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("we can't use your worthless brain anyway!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;37. INVITED TO JOIN GLADIATORS
 If gamPromo=37
  ;intro
  optionA$=translate("Yes, join gang!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i'm sure you know"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("that only the strong survive in here?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("So why not join The Gladiators and fight alongside"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("your fellow athletes? We could use a guy like you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),5)
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Welcome to the team, soldier! Wear the ink"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("with pride and we'll always have your back..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("You're either with us or against us - and this is"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("one wall you really don't want to come up against!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;38. INVITED TO JOIN THE PEAKS
 If gamPromo=38
  ;intro
  optionA$=translate("Yes, join gang!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", don't forget that the"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("point of prison is to better yourself..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Join The Peaks and we'll guide you to your"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("highest self! You'll be out before you know it..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),6)
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Welcome to the family, brother! We don't offer"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("an easy life, but it will be a meaningful one..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   Outline(translate("That's your choice, but do remember that those"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("who live by the sword will die by the same fate..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;39. CONFRONTED ABOUT ATTACKING GANG MEMBER
 If gamPromo=39
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", watch who you mess with!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(charPromoRef(pChar(cyc)))+translate(" is a member of ")+textGang$(charGang(pChar(cyc)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
    promoEffect=1
   EndIf
   Outline(translate("An attack on one of us is an attack on the"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("whole crew, so pick your battles carefully!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;40. ASSAULTED RIVAL GANG MEMBER
 If gamPromo=40
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, do you know who you're messing with?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm a member of ")+textGang$(charGang(pChar(cyc)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("This ink means something! One word from me"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and the whole crew will be on your back..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;41. ASSAULTED FELLOW GANG MEMBER
 If gamPromo=41
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", why are you attacking"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("a fellow member of ")+textGang$(charGang(pChar(cyc)))+translate("?!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charGang(pChar(v))=0 : GangAdjust(pChar(v)) : ApplyCostume(v)
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("The last thing a crew needs is civil war!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("A traitor doesn't deserve to wear that ink..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;42. DEFECTED FROM GANG
 If gamPromo=42
  If promoTim>25 And promoTim<325 And charGang(pChar(v))=0
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", how could you turn"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("your back on ")+textGang$(charGang(pChar(cyc)))+translate("?!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>25 And promoTim<325 And charGang(pChar(v))>0
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", how could you turn your back"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("on ")+textGang$(charGang(pChar(cyc)))+translate(" to join ")+textGang$(charGang(pChar(v)))+translate("?!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    AngerGang(pChar(v),charGang(pChar(cyc)))
    promoEffect=1
   EndIf
   Outline(translate("Don't you know the only way out is DEATH?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You soon will when the others hear about this!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : RemovePromo(gamPromo)
 EndIf 
 ;43. FORGIVE UNPROVOKED ATTACK
 If gamPromo=43
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Brother, what hurts you so much that you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("feel you need to hurt me to heal it?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Come, you will find that the respect of these"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("men isn't worth sacrificing your soul for..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;44. FORGIVEN FOR STEALING
 If gamPromo=44
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Brother, what is it you're hoping to attain?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Will that ")+Lower$(weapName$(weapType(pWeapon(v))))+translate(" really bring you happiness?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Detach yourself from these possessions, and you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("will discover that what matters can never be lost..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;45. FORMER GANG MEMBER ASKS YOU TO LEAVE TOO
 If gamPromo=45
  ;intro
  optionA$=translate("Yes, leave gang...") : optionB$=translate("No, forget it!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i thought you should"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("know that i've left ")+textGang$(charGang(pChar(v)))+translate("!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Why don't you leave before it's too late?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("They'll only use you to do their dirty work!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),0)
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Good for you! They're gonna come after us when"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("they hear about this, but we'll survive together..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("In that case, we can no longer be friends!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I want nothing to do with ")+textGang$(charGang(pChar(v)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;46. FRIEND JOINS A GANG
 If gamPromo=46
  ;intro
  optionA$=translate("Yes, join gang!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", you're now looking"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("at a member of ")+textGang$(charGang(pChar(cyc)))+translate("!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Do you want me to get you into the gang too?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Their support can make life easier in here!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),charGang(pChar(cyc)))
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Welcome to the family, brother! Trust me, it's"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("much better to be with us than against us..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("In that case, we can no longer be friends!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("My loyalties lie with ")+textGang$(charGang(pChar(cyc)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;47. NEW MEMBER OF YOUR GANG
 If gamPromo=47
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i'm now a member"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("of ")+textGang$(charGang(pChar(v)))+translate(" as well!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("It's great to be onboard! I hope we can"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("work together to rule this place..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;48. INMATE ASKS TO BUY ITEM
 If gamPromo=48
  ;intro
  optionA$=translate("Yes, accept $")+GetFigure$(promoCash)+"!" : optionB$=translate("No sale...") 
  If promoStage=0 And promoTim<25
   promoVariable=pWeapon(v)
   promoCash=Rnd(weapValue(weapType(promoVariable))/2,weapValue(weapType(promoVariable))+(weapValue(weapType(promoVariable))/2))
   ;promoCash=RoundOff(promoCash,5)
  EndIf
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i need that ")+Lower$(weapName$(weapType(promoVariable)))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Would you be willing to sell it for $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Great! This should come in handy."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Maybe we'll do business again some time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=4 : pWeapFoc(cyc)=promoVariable
    promoEffect=1
   EndIf
   Outline(translate("Fine! If you won't sell it, i'll have to take it!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Assholes like you deserve to be robbed..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;49. INMATE TRIES TO SELL ITEM
 If gamPromo=49
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25
   promoVariable=pWeapon(cyc)
   promoCash=Rnd(weapValue(weapType(promoVariable))/2,weapValue(weapType(promoVariable))+(weapValue(weapType(promoVariable))/2))
   ;promoCash=RoundOff(promoCash,5)
   If promoCash>gamMoney(slot) Then promoCash=gamMoney(slot)
  EndIf
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", would you be interested"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("in buying this ")+Lower$(weapName$(weapType(promoVariable)))+translate(" for $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Good for you! I hope you enjoy it."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Maybe we'll do business again some time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then DamageRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("Hey, it's your choice! But don't come crying"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("to me the next time you need something..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;50. FRIEND OFFERS ITEM
 If gamPromo=50
  ;intro
  optionA$=translate("Yes, accept item!") : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", do you want this ")+Lower$(weapName$(weapType(pWeapon(cyc))))+translate("?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I don't need it anymore, so it's up for grabs..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Consider it yours, my friend! I hope you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("get as much use out of it as i did..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then DamageRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("Hey, i was just trying to make your life easier!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Maybe somebody else in here will appreciate it..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;51. WARDEN ASKS YOU TO LEAVE GANG
 If gamPromo=51
  ;intro
  optionA$=translate("Yes, leave gang...") : optionB$=translate("No, forget it!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+CellName$(pChar(v))+translate(", let me see those tattoos!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Are you a member of ")+textGang$(charGang(pChar(v)))+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("We're cracking down on that gang, so give"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("them up or we'll make an example out of you!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    ChangeGang(pChar(v),0)
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1 
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Good for you! Those guys never cared about you."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("They just wanted someone to do their dirty work..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    randy=Rnd(0,2)
    If randy=0 And gamWarrant(slot)<2 Then gamWarrant(slot)=2
    promoEffect=1
   EndIf
   Outline(translate("You're willing to take the heat for those assholes?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Perhaps you need reminding who runs this place..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;52. WARDEN OFFERS TO OVERLOOK CRIME
 If gamPromo=52
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"..." : optionB$=translate("No, i don't care!") 
  If promoStage=0 And promoTim<25
   promoCash=gamWarrant(slot)*50
   If promoCash<50 Then promoCash=50
   promoCash=RoundOff(promoCash,10)
   If promoCash>gamMoney(slot) Then promoCash=gamMoney(slot)
  EndIf
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+CellName$(pChar(v))+translate(", i'm supposed to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("take you in for ")+Lower$(textWarrant$(gamWarrant(slot)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("Fortunately for you, i'm feeling generous!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Slip me $")+GetFigure$(promoCash)+translate(" and i'll drop the charges?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1 
    DamageRelationship(pChar(cyc),pChar(v),1)
    For count=1 To no_plays
     If charRole(pChar(count))=1 Then pAgenda(count)=1 : pNowhere(count)=99 : charAngerTim(pChar(count),pChar(v))=0
    Next
    If ChannelPlaying(chAlarm)>0 Then StopChannel chAlarm
    gamWarrant(slot)=0 : promoEffect=1
   EndIf
   Outline(translate("It seems you're not the man we're looking for!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I can't even remember what the crime was now..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("Fine! If you won't accept my help, all that"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("remains is to drag you before the judge..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;53. CARRYING ITEM IN WORKSHOP
 If gamPromo=53
  ;intro
  optionA$=translate("Yes, drop item...") : optionB$=translate("No, it's mine!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1) : promoVariable=weapType(pWeapon(v))
   Outline(translate("Hey, ")+CellName$(pChar(v))+translate(", stop where you are! What"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("are you doing with that ")+Lower$(weapName$(promoVariable))+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("This is where you get paid for making items -"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("not taking them! Put that back where it belongs..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    charAngerTim(pChar(cyc),pChar(v))=0 : promoEffect=1
   EndIf
   Outline(translate("That's right. Step away from the ")+Lower$(weapName$(promoVariable)),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and we won't have to take this any further..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    randy=Rnd(0,5)
    If randy=0 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    If pWeapon(v)>0 And gamMission(slot)<>11 And gamMission(slot)<>12
     If randy=1And gamWarrant(slot)<4 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(v)
     If randy=2 And gamWarrant(slot)<7 Then gamWarrant(slot)=7 : gamItem(slot)=pWeapon(v)
    EndIf
    promoEffect=1
   EndIf
   Outline(translate("You morons never know when to quit! You could"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("have earned, but now i'm gonna make you pay..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;54. ANGRY ABOUT MUTILATION
 If gamPromo=54
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", look what you've done!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'll be scarred for life because of you!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    charAngerTim(pChar(cyc),pChar(v))=100000
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("I'm never gonna forgive your for this!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I won't stop until you feel the same pain..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;55. WITNESS BLACKMAILS YOU
 If gamPromo=55
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"..." : optionB$=translate("No, i don't care!") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i saw what you did!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I could send you down for a long time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("Fortunately for you, i'm a compassionate man and"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("may be willing to forget what i saw for $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),1) 
    charWitness(pChar(v))=0 : gamWarrant(slot)=0
    If ChannelPlaying(chAlarm)>0 Then StopChannel chAlarm
    promoEffect=1
   EndIf
   Outline(translate("Turns out i didn't see anything after all!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I guess the wardens just lost their case..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("Fine, have it your way! When the wardens hear my"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("version of the story they'll throw away the key..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;56. INMATE OFFERS TO TAKE BLAME
 If gamPromo=56
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"..." : optionB$=translate("No, i don't care!") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", what have you been up to?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Word is you're wanted for ")+Lower$(textWarrant$(gamWarrant(slot)))+translate("!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("Fortunately for you, i need money - and may be"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("willing to take the blame if you pay me $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    For count=1 To no_plays
     If charRole(pChar(count))=1 Then charAngerTim(pChar(count),pChar(cyc))=1000 : pAgenda(count)=2 : pFollowFoc(count)=cyc
    Next
    charSentence(pChar(cyc))=charSentence(pChar(cyc))+180
    charWitness(pChar(v))=0 : gamWarrant(slot)=0
    If ChannelPlaying(chAlarm)>0 Then StopChannel chAlarm
    promoEffect=1
   EndIf
   Outline(translate("This should ease the pain when i take the heat!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm never getting out, so i might as well profit..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    promoEffect=1
   EndIf
   Outline(translate("Fine, have it your way! I'll take the side"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("of the wardens and make sure you go down..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;57. INMATE ASKS YOU TO TAKE BLAME
 If gamPromo=57
  ;intro
  optionA$=translate("Yes, accept $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25
   promoVariable=Rnd(1,14)
   promoCash=Rnd(promoVariable*50,promoVariable*100)
   promoCash=RoundOff(promoCash,50)
  EndIf
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", you've got to help me!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("The wardens want me for ")+Lower$(textWarrant$(promoVariable))+translate("!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Take the heat for me and i won't forget it!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'll even pay you $")+GetFigure$(promoCash)+translate(" for your trouble?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    gamWarrant(slot)=promoVariable
    gamVictim(slot)=Rnd(1,no_chars)
    If gamMission(slot)<>11 And gamMission(slot)<>12 Then gamItem(slot)=Rnd(1,no_weaps)
    promoEffect=1
   EndIf
   Outline(translate("Thanks, you just saved my life! Here's your money."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Having a friend like you will be worth every penny..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    DamageRelationship(pChar(cyc),pChar(v),-1)
    charReputation(pChar(v))=charReputation(pChar(v))-1
    For count=1 To no_plays
     If charRole(pChar(count))=1 Then charAngerTim(pChar(count),pChar(cyc))=1000 : pAgenda(count)=2 : pFollowFoc(count)=cyc
    Next
    promoEffect=1
   EndIf
   Outline(translate("You're turning down $")+GetFigure$(promoCash)+translate(" to tell a few lies?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Don't come to me the next time you need a favour..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;58. WARDEN THREATENS TO MAKE UP CHARGE
 If gamPromo=58
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"..." : optionB$=translate("No, do your worst!") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("You know, ")+CellName$(pChar(v))+translate(", being a warden is a very"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("respectable job. People believe whatever you say!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("For instance, i don't have to SEE you morons"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("committing a crime - all i have to do is SAY it!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>675 And promoTim<975
   Speak(cyc,1)
   Outline(translate("Give me $")+GetFigure$(promoCash)+translate(" or i'll give you an example!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I could have you before a judge right now..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>975 Then camFoc=v
  If promoStage=0 And promoTim>1000 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("A wise choice! It would have been a shame"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("if you ruined your life for no reason..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))/2
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1) 
    gamWarrant(slot)=Rnd(1,14)
    gamVictim(slot)=Rnd(1,no_chars)
    If gamMission(slot)<>11 And gamMission(slot)<>12 Then gamItem(slot)=Rnd(1,no_weaps)
    promoEffect=1
   EndIf
   Outline(translate("Wrong move! It turns out you're wanted for"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(Lower$(textWarrant$(gamWarrant(slot)))+translate("! I'll have to take you in..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;59. WARDEN AKS YOU TO GIVE YOURSELF IN
 If gamPromo=59
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("You're wanted for ")+Lower$(textWarrant$(gamWarrant(slot)))+translate(", ")+CellName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("It's only a matter of time before we catch you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("Give yourself up! The sooner you face the charges,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("the sooner you can get on with your rehabilitation..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;60. TIME TO GO
 If gamPromo=60
  ;intro
  optionA$=translate("Yes, let me go!") : optionB$=translate("No, not yet...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("You've served your sentence, ")+CellName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Would you like me to escort you outside?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   Outline(translate("OK, you're a free man now so i wish you luck!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("We'll keep you posted on what happens in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   Outline(translate("Alright, i'll give you some time to say goodbye."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You'll have to leave this place eventually though..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;61. LOSE WEIGHT
 If gamPromo=61
  If promoTim<100 And pAnim(cyc)<20 Then ChangeAnim(cyc,130)
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    pHealth(cyc)=pHealth(cyc)+5
    charStrength(pChar(cyc))=charStrength(pChar(cyc))-5
    charAgility(pChar(cyc))=charAgility(pChar(cyc))+5
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+5
    gamGrowth(slot)=0 : promoEffect=1
   EndIf
   Outline(translate("Whoa, i think i've lost some weight!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I certainly feel lighter on my feet..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;62. GAIN WEIGHT
 If gamPromo=62
  If promoTim<100 And pAnim(cyc)<20 Then ChangeAnim(cyc,130) 
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    If charModel(pChar(cyc))=<3 Then pHealth(cyc)=pHealth(cyc)+5 Else pHealth(cyc)=pHealth(cyc)-5
    charStrength(pChar(cyc))=charStrength(pChar(cyc))+5
    charAgility(pChar(cyc))=charAgility(pChar(cyc))-5
    If charModel(pChar(cyc))=<3 Then charHappiness(pChar(cyc))=charHappiness(pChar(cyc))+5 Else charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-5
    gamGrowth(slot)=0 : promoEffect=1
   EndIf
   Outline(translate("Whoa, i think i've gained some weight!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I certainly feel a lot more powerful..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;63. WRONG NUMBER
 If gamPromo=63
  If promoTim<25
   Repeat
    promoVariable=Rnd(1,no_chars)
   Until promoVariable<>gamChar(slot)
  EndIf
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hello? Who's that?! Sorry, i was hoping to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("speak to somebody called '")+charName$(promoVariable)+translate("'..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;64. SOCIAL CALL
 If gamPromo=64
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hi, ")+charName$(gamChar(slot))+translate(", how are you holding up?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I was just calling to check you're alright!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   If promoEffect=0 Then charHappiness(pChar(v))=charHappiness(pChar(v))+5 : promoEffect=1
   Outline(translate("We all miss you back to home, and we hope"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("you get out soon! Keep your head up, friend..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;65. FAMILY CALL
 If gamPromo=65
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hi, darling, it's your wife here. We miss you!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I thought you might like to talk to the kids?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promotim>325 Then camFoc=v 
  If promoTim>350 And promoTim<650
   Speak(v,3)  
   Outline(translate("Hi, kids! I know you wish i was home, but"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("daddy has got some important work to do..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(v,3)  
   If promoEffect=0 Then charHappiness(pChar(v))=charHappiness(pChar(v))+5 : promoEffect=1
   Outline(translate("I'm a 'secret agent' like we saw on TV!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Be good while i'm saving the world! Bye..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;66. LAWYER OFFERS TO SHAVE A DAY OFF
 If gamPromo=66
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No, forget it...") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(2)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hi, ")+charName$(pChar(v))+translate(", it's your lawyer speaking."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I've got some good news about your case!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("One of those idiots messed up your paperwork,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so we should be able to claim back a day!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("The only problem is we'll need $")+GetFigure$(promoCash)+translate(" to file"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("an appeal. Do you want to wire me the money?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>975 Then camFoc=v
  If promoStage=0 And promoTim>1000 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    PlaySound sCash : statTim(6)=100 : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charSentence(pChar(v))=charSentence(pChar(v))-1
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    promoEffect=1
   EndIf
   Outline(translate("Alright, i'll get onto it immediately! Your"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("sentence should be down to just ")+Lower$(GetFigure$(charSentence(pChar(v))))+translate(" days..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   Outline(translate("Damn, i thought we were onto something here!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I guess you don't care about getting out..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;67. GANG REDUCES YOUR SENTENCE
 If gamPromo=67
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hey, ")+charName$(gamChar(slot))+translate(", i hear you're doing a good"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("job of representing ")+textGang$(charGang(gamChar(slot)))+translate(" in there?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I want you out of prison as soon as possible"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so that i can put you to work on the street!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   If promoEffect=0 
    charSentence(pChar(v))=charSentence(pChar(v))-1 : statTim(6)=100
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    promoEffect=1
   EndIf
   Outline(translate("I'll pull some strings with the wardens and"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("try to get some time shaved off your sentence..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;68. SELL STORY TO JOURNALIST
 If gamPromo=68
  ;intro
  optionA$=translate("Yes, accept $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=Rnd(10,100) : promoCash=promoCash*10
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Listen, i'm a journalist and i'm trying to put"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("together a piece about life inside prison..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I'd love to hear about your experiences! Would"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("you be willing to share your story for $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    nearest=NearestEnemy(v)
    If InProximity(v,nearest,50) And charPromo(pChar(nearest),pChar(v))=0 Then charPromo(pChar(nearest),pChar(v))=70
    promoEffect=1
   EndIf
   Outline(translate("Thanks for taking part! I'm sure the public"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("will be fascinated by what you have to say..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   Outline(translate("You're turning down $")+GetFigure$(promoCash)+translate(" to talk to me?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("No one cares what you morons have to say anyway..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;69. SELL MOVIE RIGHTS
 If gamPromo=69
  ;intro
  optionA$=translate("Yes, accept $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=Rnd(10,100) : promoCash=promoCash*10
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Listen, i'm a filmmaker and i'd like to make a"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("project about the rise and fall of a criminal!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Would you be interested in taking part if"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("i paid $")+GetFigure$(promoCash)+translate(" for the rights to your story?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    nearest=NearestEnemy(v)
    If InProximity(v,nearest,50) And charPromo(pChar(nearest),pChar(v))=0 Then charPromo(pChar(nearest),pChar(v))=70
    promoEffect=1
   EndIf
   Outline(translate("Thanks for taking part! I'm sure the public"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("will be fascinated by your life story..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   Outline(translate("You're turning down $")+GetFigure$(promoCash)+translate(" to become a star?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("No one cares about your pathetic life anyway..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;70. SNITCH FOR SELLING STORY
 If gamPromo=70
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("I hope the money was worth it, you rat!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Nobody around here will trust you again..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;71. INITIATED AT MAIN HALL
 If gamPromo=71
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Alright, ")+charName$(pChar(v))+translate(", that's you processed!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're now known as Prisoner ")+CellName$(pChar(v))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250) 
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("That means you're in Cell ")+charCell(pChar(v))+translate(" of the ")+textBlock$(charBlock(pChar(v)))+translate(" Block,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so head over there and make yourself at home..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;72. CAUGHT USING ITEM AS WEAPON
 If gamPromo=72
  ;intro
  optionA$=translate("Yes, drop item...") : optionB$=translate("No, it's mine!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1) : promoVariable=weapType(pWeapon(v))
   Outline(translate("Hey, ")+CellName$(pChar(v))+translate(", i saw what you did to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(charPromoRef(pChar(cyc)))+translate(" with that ")+Lower$(weapName$(promoVariable))+translate("!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You shouldn't be fighting at all - let alone with"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("weapons! Put that down or there'll be trouble..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    charAngerTim(pChar(cyc),pChar(v))=0 : promoEffect=1
   EndIf
   Outline(translate("That's right. Step away from the ")+Lower$(weapName$(promoVariable)),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and we won't have to take this any further..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    randy=Rnd(0,5)
    If randy=0 And gamWarrant(slot)<1 Then gamWarrant(slot)=1
    If randy=1 And gamWarrant(slot)<4 And pWeapon(v)>0 And gamMission(slot)<>11 And gamMission(slot)<>12 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(v)
    If randy=2 And gamWarrant(slot)<10 And pWeapon(v)>0 Then gamWarrant(slot)=10 : gamItem(slot)=pWeapon(v)
    promoEffect=1
   EndIf
   Outline(translate("You think i'm scared of a ")+Lower$(weapName$(promoVariable))+translate("?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'll take it and shove it up your ass!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : promoUsed(1)=1 : promoUsed(18)=1 : promoUsed(53)=1
 EndIf
 ;73. GUARD OFFERS IMMUNITY
 If gamPromo=73
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1) 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Listen, ")+CellName$(pChar(v))+translate(", i understand that a prisoner"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("needs to do his dirt to survive in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("That's why i'd like to help! Give me $")+GetFigure$(promoCash),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and i'll cut you some slack for an hour or so?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    charBribeTim(pChar(cyc))=5000
    promoEffect=1
   EndIf
   Outline(translate("Thanks! This should buy you a little breathing"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("space. Just stay away from the other wardens..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("In that case, i'll ride you harder than ever!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("It's better to be with me than against me..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;74. INMATE OFFERS PROTECTION
 If gamPromo=74
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(2)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Prison can be a cold place, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You need somebody to look out for you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I can offer that protection! Give me $")+GetFigure$(promoCash),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("and i'll watch your back for an hour or so?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    charFollowTim(pChar(cyc))=5000
    charBribeTim(pChar(cyc))=charFollowTim(pChar(cyc))
    promoEffect=1
   EndIf
   Outline(translate("Thanks! This should buy you some peace of mind."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Do your thing and leave the worrying to me..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then DamageRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("Fine, then add me to your list of enemies!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I guess you just can't help some people..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;75. TIME UP ON IMMUNITY
 If gamPromo=75
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : charBribeTim(pChar(cyc))=0
   Outline(translate("Time's up, ")+CellName$(pChar(v))+translate("! I have to get back to work,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so don't step out of line from now on..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;76. TIME UP ON PROTECTION
 If gamPromo=76
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : pAgenda(cyc)=1 : pNowhere(cyc)=99
   charFollowTim(pChar(cyc))=0 : charBribeTim(pChar(cyc))=0
   Outline(translate("I've got to go now, ")+charName$(pChar(v))+translate(", but"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("maybe we'll do business again some time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;77. FRIEND COMES TO AID
 If gamPromo=77
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    If charAngerTim(pChar(cyc),charPromoRef(pChar(cyc)))<1000 Then charAngerTim(pChar(cyc),charPromoRef(pChar(cyc)))=1000
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Don't worry, ")+charName$(pChar(v))+translate("! I'm gonna get"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(charPromoRef(pChar(cyc)))+translate(" for what he did to you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;78. THANKED FOR INTERVENING
 If gamPromo=78
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    ChangeRelationship(pChar(cyc),charPromoRef(pChar(cyc)),-1)
    promoEffect=1
   EndIf
   Outline(translate("Thanks for helping me out, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(charPromoRef(pChar(cyc)))+translate(" needs to be taught a lesson..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;79. UNGRATEFUL FOR INTERVENING
 If gamPromo=79
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    ChangeRelationship(pChar(cyc),charPromoRef(pChar(cyc)),-1)
    promoEffect=1
   EndIf
   Outline(translate("Stay out of my business, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I can handle a punk like ")+charName$(charPromoRef(pChar(cyc)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;80. FALL OUT AFTER HITTING FRIEND
 If gamPromo=80
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", what's your problem?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I thought we were friends, but i guess not..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;81. PAY SOMEBODY TO ATTACK ENEMY
 If gamPromo=81
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=<1 And promoTim>25 Then ShowPhoto(charPromoRef(pChar(cyc)))
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(2)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear you've been"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("having trouble with ")+charName$(charPromoRef(pChar(cyc)))+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I can take care of him if you want? Just give"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("me $")+GetFigure$(promoCash)+translate(" and he'll never bother you again!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charReputation(pChar(v))=charReputation(pChar(v))+1
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    ChangeRelationship(pChar(cyc),charPromoRef(pChar(cyc)),-1) 
    charAngerTim(pChar(cyc),charPromoRef(pChar(cyc)))=10000
    For count=1 To no_plays
     If pChar(count)=charPromoRef(pChar(cyc)) Then pAgenda(cyc)=2 : pFollowFoc(cyc)=count
    Next
    promoEffect=1
   EndIf
   Outline(translate("Consider it done! As soon as i see that"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("asshole, he'll wish he'd never been born..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    promoEffect=1
   EndIf 
   Outline(translate("Maybe it's YOU that deserves a beating!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(charPromoRef(pChar(cyc)))+translate(" would pay to see that..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;82. CONFRONTED ABOUT KILLING FRIEND
 If gamPromo=82
  If promoTim=>25 And promoTim<9975 Then ShowPhoto(charPromoRef(pChar(cyc)))
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i know that you were"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("responsible for ")+charName$(charPromoRef(pChar(cyc)))+translate("'s death!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    charAngerTim(pChar(cyc),pChar(v))=100000
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("He was a dear friend of mine, and i won't"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("rest until you pay for what you've done!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;83. CONGRATULATED FOR KILLING ENEMY
 If gamPromo=83
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : ShowPhoto(charPromoRef(pChar(cyc)))
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", thanks for getting rid"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("of ")+charName$(charPromoRef(pChar(cyc)))+translate("! I wish i'd done it myself..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;84. FRIEND LIKES YOU BY ASSOCIATION
 If gamPromo=84
  If promoTim=>25 And promoTim<9975 Then ShowPhoto(charPromoRef(pChar(cyc)))
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear you're friends with"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(charPromoRef(pChar(cyc)))+translate("? Me too, so it's nice to meet you!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Any friend of his is a friend of mine,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so let me know if you ever need a favour..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;85. ENEMY HATES YOU BY ASSOCIATION
 If gamPromo=85
  If promoTim=>25 And promoTim<9975 Then ShowPhoto(charPromoRef(pChar(cyc)))
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear you've got a problem"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("with ")+charName$(charPromoRef(pChar(cyc)))+translate("? Well, he's a friend of mine!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    promoEffect=1
   EndIf
   Outline(translate("If he doesn't like you, i don't like you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("either - so you better watch your back!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;86. ASKED TO GIVE UP FRIEND
 If gamPromo=86
  ;intro
  optionA$=translate("Yes, give up friend...") : optionB$=translate("No, forget it!") 
  If promoStage=<1 And promoTim>25 Then ShowPhoto(charPromoRef(pChar(cyc)))
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", why do you hang out"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("with an asshole like ")+charName$(charPromoRef(pChar(cyc)))+translate("?!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("Cut that loser out of your life, or we'll"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("have to assume that you're just as bad!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),1) 
    ChangeRelationship(pChar(v),charPromoRef(pChar(cyc)),-1)
    If charPromo(charPromoRef(pChar(cyc)),gamChar(slot))=0
     charPromo(charPromoRef(pChar(cyc)),gamChar(slot))=87
     charPromoRef(charPromoRef(pChar(cyc)))=pChar(cyc)
    EndIf 
    promoEffect=1
   EndIf
   Outline(translate("A wise choice! It would have been a shame"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("if you ruined your life because of that guy..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<1000 Then charAngerTim(pChar(cyc),pChar(v))=1000
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("That tells me everything i need to know about you!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("If you share his company, you'll share his fate..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;87. CONFRONTED ABOUT BETRAYAL
 If gamPromo=87
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : ShowPhoto(charPromoRef(pChar(cyc)))
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i can't believe that you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("sold me out to be friends with ")+charName$(charPromoRef(pChar(cyc)))+translate("?!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    promoEffect=1
   EndIf
   Outline(translate("If that's your idea of friendship then i'm"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("glad to have you out of my life, you traitor!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;88. REACTION TO GUILTY VERDICT
 If gamPromo=88
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Don't listen to that judge, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("His 'guilty' verdict makes you innocent to us..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("It's the ones that come back from court without"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("doing their time that you have to be suspicious of..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : RemovePromo(gamPromo)
 EndIf 
 ;89. REACTION TO INNOCENT VERDICT
 If gamPromo=89
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("You're a snitch, ")+charName$(pChar(v))+translate("! Who did you"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("give up to get that judge off your back?!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("When a man comes back from court without getting"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("time, there can only be one explanation for it..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : RemovePromo(gamPromo)
 EndIf
 ;90. PEAKS SHUN CRIMINAL
 If gamPromo=90
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Your actions have brought shame on us,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(pChar(v))+translate(". This is the end for you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   If promoEffect=0
    ChangeGang(pChar(v),0)
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("We wish you luck with your rehabilitation, but"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("i'm afraid it cannot continue under our banner..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : RemovePromo(gamPromo)
 EndIf
 ;91. CAUGHT IN FRIEND'S CELL
 If gamPromo=91
  If promoTim=>25 And promoTim<9975 Then ShowPhoto(charPromoRef(pChar(cyc)))
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", what are you doing"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("in there?! That's ")+charName$(charPromoRef(pChar(cyc)))+translate("'s cell..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))+1
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    pSubX#(cyc)=9999 : pSubZ#(cyc)=9999
    promoEffect=1
   EndIf
   Outline(translate("He happens to be a friend of mine, so get"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("out before i kick you out on his behalf!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;92. INMATE ASKS TO JOIN GANG
 If gamPromo=92
  ;intro
  optionA$=translate("Yes, recruit member...") : optionB$=translate("No, forget it!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Are you a member of ")+textGang$(charGang(pChar(v)))+translate("?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I've always wanted to join that gang!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Do you think you could find a place for me?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm sure i'd be a great asset to the cause..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    ChangeGang(pChar(cyc),charGang(pChar(v)))
    For char=1 To no_chars
     suitable=1
     If charGang(char)=>1 And charGang(char)=<3 And GetRace(pChar(cyc))+1<>charGang(char) Then suitable=0
     If charGang(char)=4 And charIntelligence(pChar(cyc))<70 Then suitable=0
     If charGang(char)=5 And (charStrength(pChar(cyc))+charAgility(pChar(cyc))<140 Or charModel(pChar(cyc))=>4) Then suitable=0
     If charGang(char)=charGang(pChar(v)) And char<>pChar(cyc) And char<>pChar(v) And charPromo(char,gamChar(slot))=0
      If suitable=0 Then charPromo(char,gamChar(slot))=264
      If suitable=1 Then charPromo(char,gamChar(slot))=265
     EndIf
    Next
    promoEffect=1
   EndIf
   Outline(translate("Great! You won't regret this, i promise!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'll do anything for ")+textGang$(charGang(pChar(v)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then DamageRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("Who wants to join your pathetic club anyway?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Everybody knows you're a laughing stock here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;93. PAY YOUR WAY INTO GANG
 If gamPromo=93
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i'm sure you'd like to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("be a member of ")+textGang$(charGang(pChar(cyc)))+translate(" like me?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Trouble is there's a strict selection process,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("but $")+GetFigure$(promoCash)+translate(" might tempt me to overlook it?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    ChangeGang(pChar(v),charGang(pChar(cyc)))
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Thanks for your generous contribution! I'm"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("sure you'll be a great asset to the gang..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("You'll never make any progress in here with that"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("attitude! You need to learn to grease the wheels..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;94. GANG REQUIREMENTS
 If gamPromo=94
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i'm sure you'd like to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("be a member of ")+textGang$(charGang(pChar(cyc)))+translate(" like me?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   If charGang(pChar(cyc))=<3
    Outline(translate("Well, dream on - because you need a tough"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("reputation if you want to hang with us!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If charGang(pChar(cyc))=4
    Outline(translate("Well, dream on - because you need to be"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("intelligent if you want to hang with us!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If charGang(pChar(cyc))=5
    Outline(translate("Well, dream on - because you need to be"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("physically fit to keep the pace with us!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If charGang(pChar(cyc))=6
    Outline(translate("Well, dream on - because your reputation"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("is too violent for you to be one of us!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;95. GANG ASKS YOU TO KICK UP
 If gamPromo=95
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"..." : optionB$=translate("No, it's mine!") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(2)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", nobody said being a"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("member of ")+textGang$(charGang(pChar(v)))+translate(" was free!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("You need to kick up some of that money you've"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("earned in our name! $")+GetFigure$(promoCash)+translate(" should cover it..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("You're a good earner, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Just remember that the family comes first..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    ChangeGang(pChar(v),0)
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-5
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("In that case, take your ass somewhere else!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("We haven't got any room for passengers..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;96. GANG ASKS YOU TO CONFORM
 If gamPromo=96
  ;intro
  optionA$=translate("Yes, conform to gang...") : optionB$=translate("No, leave me alone!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", ")+textGang$(charGang(pChar(v))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("aren't supposed to dress like that!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,1)
   Outline(translate("Return to how you were when you joined us,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("or we'll have to question your loyalty!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    GangAdjust(pChar(v)) : ApplyCostume(v)
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Good! That looks much better!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Now don't let it happen again..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    ChangeGang(pChar(v),0)
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-5
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("If you're so ashamed of us then get out!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("We don't need traitors like you in the gang..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;97. ENEMY ASKS TO BURY THE HATCHET
 If gamPromo=97
  ;intro
  optionA$=translate("Yes, make friends...") : optionB$=translate("No, forget it!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Listen, ")+charName$(pChar(v))+translate(", i know we haven't"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("been seeing eye-to-eye in recent weeks..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("Well, i for one am tired of the bickering"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("so what d'you say we put it all behind us?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Great! That's one less thing to worry about!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You need all the friends you can get in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    promoEffect=1
   EndIf
   Outline(translate("Fine! We'll wage war until you stop breathing!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You'll learn that pride comes before a fall..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;98. FRIENDLY WELCOME
 If gamPromo=98
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If charGang(pChar(cyc))=0
    Outline(translate("Welcome to the jungle! My name is ")+charName$(pChar(cyc))+translate(","),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and i live in Cell ")+charCell(pChar(cyc))+translate(" of the ")+textBlock$(charBlock(pChar(cyc)))+translate(" Block..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If charGang(pChar(cyc))>0
    Outline(translate("Welcome to the jungle! I'm ")+charName$(pChar(cyc))+translate(","),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and i'm a member of ")+textGang$(charGang(pChar(cyc)))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Life can be pretty tough inside this place,"),rX#(400),rY#(520),30,30,30,250,250,250)
   If charGang(pChar(cyc))=0 Then Outline(translate("so look me up if you ever need a friend..."),rX#(400),rY#(560),30,30,30,250,250,250)
   If charGang(pChar(cyc))>0 Then Outline(translate("so look us up if you ever need some support..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;99. NEUTRAL WELCOME
 If gamPromo=99
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   If charGang(pChar(cyc))=0
    Outline(translate("I'm ")+charName$(pChar(cyc))+translate(" from Cell ")+charCell(pChar(cyc))+translate(" of the ")+textBlock$(charBlock(pChar(cyc)))+translate(" Block."),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Stay out of my way and we won't have a problem..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If charGang(pChar(cyc))>0
    Outline(translate("I'm ")+charName$(pChar(cyc))+translate(" - a member of ")+textGang$(charGang(pChar(cyc)))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Stay out of my way and we won't have a problem..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;100. HOSTILE WELCOME
 If gamPromo=100
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   If charGang(pChar(cyc))=0
    Outline(translate("Watch your back, new boy! I'm ")+charName$(pChar(cyc))+translate(" from"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Cell ")+charCell(pChar(cyc))+translate(" of the ")+textBlock$(charBlock(pChar(cyc)))+translate(" Block and i rule this place..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If charGang(pChar(cyc))>0
    Outline(translate("Watch your back, new boy! I'm ")+charName$(pChar(cyc))+translate(" of"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(textGang$(charGang(pChar(cyc)))+translate(" and we rule this place..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;100-140: CRIME PROMOS
 CrimePromos(cyc,v,y#)
 ;140-200: MISSION PROMOS
 MissionPromos(cyc,v,y#)
 ;200-300: ADDITIONAL PROMOS
 ;200. FRIENDLY NEW ARRIVAL
 If gamPromo=200
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Hi, ")+charName$(pChar(v))+translate(", my name is ")+charName$(pChar(cyc))+translate("."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm new here, but i hope we can be friends!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;201. HOSTILE NEW ARRIVAL
 If gamPromo=201
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, there's a new king in town and his name"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("is ")+charName$(pChar(cyc))+translate("! Watch your step around me..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;202. FRIENDLY ROOM-MATE
 If gamPromo=202
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Well, it looks like we're sharing this cell!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Don't worry, i'm sure we'll get along fine..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;203. NEUTRAL ROOM-MATE
 If gamPromo=203
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("So i guess i've got to share this cell with you?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Just stay out of my way and we'll be alright..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf
 ;204. HOSTILE ROOM-MATE
 If gamPromo=204
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("Damn, i can't believe i have to share a cell!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You better stay in the corner and shut up..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 ;: promoUsed(gamPromo)=1
 EndIf 
 ;205. NEW CELL
 If gamPromo=205
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", the prison system has been"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("shaken up to stop you getting too comfortable!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   If promoEffect=0 
    AssignCell(gamChar(slot))
    ApplyCostume(v)
    FindCellMates()
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    promoEffect=1
   EndIf
   Outline(translate("You're now in Cell ")+charCell(pChar(v))+translate(" of the ")+textBlock$(charBlock(pChar(v)))+translate(" Block."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Head over there and make yourself at home..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;206. TANOY ANNOUNCES POWER FAILURE!
 If gamPromo=206
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("ATTENTION! The prison seems be"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("suffering from a power failure!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("Please be patient while the problem"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("is rectified by our technical staff..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
  gamBlackout(slot)=Rnd(1000,10000)
 EndIf 
 ;207. TANOY ANNOUNCES BOMB THREAT!
 If gamPromo=207
  If promoEffect=0
   ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1)
   For char=1 To no_chars
    charHappiness(char)=charHappiness(char)/2
   Next
   promoEffect=1
  EndIf
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("ATTENTION! The prison has been"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("targeted for a terrorist attack!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("All inmates should find a safe place to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("hide until the threat has been removed..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
  gamBombThreat(slot)=Rnd(500,5000)
 EndIf
 ;208. CALLER ISSUES BOMB THREAT!
 If gamPromo=208
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("YOU'RE ALL GOING TO DIE!!!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("We've rigged the prison with explosives..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   If promoEffect=0 Then charHappiness(gamChar(slot))=charHappiness(gamChar(slot))/2 : promoEffect=1
   Outline(translate("Run for your life like a coward - or accept"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("that your blood will be spilt for the cause!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
  gamBombThreat(slot)=Rnd(500,5000)
 EndIf
 ;209. ALARMED BY DANGEROUS WEAPON
 If gamPromo=209
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0 
    charHappiness(gamChar(slot))=charHappiness(gamChar(slot))-5
    charReputation(gamChar(slot))=charReputation(gamChar(slot))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   For count=1 To no_plays
    If charRole(pChar(count))=0 And pChar(count)<>gamChar(slot)
     pSubX#(count)=9999 : pSubZ#(count)=9999
     pAgenda(count)=1 : pExploreY#(count)=10
     If pX#(v)<0 Then pExploreX#(count)=pX#(count)+300 Else pExploreX#(count)=pX#(count)-300
     If pZ#(v)<0 Then pExploreZ#(count)=pZ#(count)+300 Else pExploreZ#(count)=pZ#(count)-300
     pRunTim(count)=200
    EndIf
    If charRole(pChar(count))=1  
     pSubX#(count)=9999 : pSubZ#(count)=9999
     pAgenda(count)=2 : pFollowFoc(count)=v
     pRunTim(count)=200
    EndIf
   Next
   Outline(translate("EVERYBODY RUN FOR YOUR LIVES!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(charName$(pChar(v))+translate(" has got a ")+Lower$(weapName$(weapType(pWeapon(v))))+translate("!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf  
 ;210. WELCOME TO YARD
 If gamPromo=210
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Welcome to the Exercise Yard, ")+CellName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("This is where you come to improve your body..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("You can improve your strength by lifting weights,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("or improve your agility by running around the yard..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("But if that sounds too boring, you could always"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("try shooting hoops! It's a fun way to keep fit..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;211. WELCOME TO STUDY
 If gamPromo=211
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Welcome to the Study, ")+CellName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("People come here to expand their minds..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("You can improve your intelligence by reading"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("a book, or earn money by sorting those files..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("If you know how to use a computer, you might even"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("want to access some information about your peers!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;212. WELCOME TO HOSPITAL
 If gamPromo=212
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Welcome to the Medical Bay, ")+CellName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("This is where you come if you feel weak..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("You can rest your bones on one of the beds, or"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("help the healing process with a dose of drugs..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("If you know what you're doing, you could even"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("earn good money by concocting the chemicals!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;213. WELCOME TO KITCHEN
 If gamPromo=213
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Welcome to the Canteen, ")+CellName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Meals are served here everyday at 13:00..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("You can also earn a little bit of money by"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("helping to prepare food behind the counter..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;214. WELCOME TO HALL
 If gamPromo=214
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Welcome to the Main Hall, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("This is the heart of the entire prison..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("There's not much to do, but you can occupy"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("your mind by watching TV or using the computer..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("It's worth keeping an eye on the phones too,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("because we get some interesting calls here!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;215. WELCOME TO WORKSHOP
 If gamPromo=215
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Welcome to the Workshop, ")+CellName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("This is where items are created..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("You get paid for everything you produce,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("but you have to leave it on the bench..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;216. WELCOME TO TOILETS
 If gamPromo=216
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Welcome to the Bathroom, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("A lot of shady stuff happens in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("This is the one place the wardens aren't"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("allowed, so you can do whatever you want!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;217. SADNESS ABOUT DEAD FRIEND
 If gamPromo=217
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : ShowPhoto(charPromoRef(pChar(cyc)))
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Have you heard about ")+charName$(charPromoRef(pChar(cyc)))+translate("'s death?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("That's sad news. He was a good friend of mine..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;218. HAPPINESS ABOUT DEAD ENEMY
 If gamPromo=218
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : ShowPhoto(charPromoRef(pChar(cyc)))
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Have you heard about ")+charName$(charPromoRef(pChar(cyc)))+translate("'s death?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I guess he finally got what was coming to him!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;219. SADNESS ABOUT RELEASED FRIEND
 If gamPromo=219
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : ShowPhoto(charPromoRef(pChar(cyc)))
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Have you heard about ")+charName$(charPromoRef(pChar(cyc)))+translate("'s release?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Good for him! He deserves a better life..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;220. HAPPINESS ABOUT RELEASED ENEMY
 If gamPromo=220
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : ShowPhoto(charPromoRef(pChar(cyc)))
   If charRole(pChar(cyc))=0 And promoEffect=0 Then ChangeRelationship(pChar(cyc),pChar(v),1) : promoEffect=1
   Outline(translate("Have you heard about ")+charName$(charPromoRef(pChar(cyc)))+translate("'s release?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("He doesn't deserve it, but i'm glad he's gone!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;221. FRIEND LOOKS FORWARD TO IMMINENT RELEASE
 If gamPromo=221
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i'm getting out of here soon!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I just wanted to say goodbye before i leave..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;222. ENEMY BOASTS ABOUT IMMINENT RELEASE
 If gamPromo=222
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i'm getting released soon!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'll be living it up while you're in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;223. CONGRATULATED ABOUT IMMINENT RELEASE
 If gamPromo=223
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear you get out soon?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("We'll miss you, but good luck out there!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;224. BITTER ABOUT IMMINENT RELEASE
 If gamPromo=224
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<10000 Then charAngerTim(pChar(cyc),pChar(v))=10000
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear you get out soon?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("If it's up to me, you'll leave in a wheelchair!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;225. GANG FRICTION
 If gamPromo=225
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, it's time for ")+textGang$(charGang(pChar(v)))+translate(" to go!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(textGang$(charGang(pChar(cyc)))+translate(" rule this place now..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    For char=1 To no_chars
     For count=1 To no_chars 
      If (charGang(char)=charGang(pChar(cyc)) And charGang(count)=charGang(pChar(v))) Or (charGang(count)=charGang(pChar(cyc)) And charGang(char)=charGang(pChar(v)))
       ChangeRelationship(char,count,-1)
       If charAngerTim(char,count)<10000 Then charAngerTim(char,count)=10000
      EndIf
     Next
    Next
    promoEffect=1
   EndIf
   Outline(translate("You better prepare your troops - because we're"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("gonna wage war until there's only one gang left!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;226. INSULTED BECAUSE OF STRENGTH
 If gamPromo=226
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("You're a pathetic specimen, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I could crush a weakling like you with one hand..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;227. PRAISED BECAUSE OF STRENGTH
 If gamPromo=227
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("I wish i was as strong as you, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm sure it gets you through some tough times..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;228. INSULTED BECAUSE OF AGILITY
 If gamPromo=228
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", you're a lazy asshole!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I can probably walk faster than you run..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;229. PRAISED BECAUSE OF AGILITY
 If gamPromo=229
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("I wish i was as fit as you, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm sure it makes life a lot easier in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;230. INSULTED BECAUSE OF INTELLIGENCE
 If gamPromo=230
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", you must be retarded!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I've never met anybody as stupid as you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;231. PRAISED BECAUSE OF INTELLIGENCE
 If gamPromo=231
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("I wish i was as smart as you, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I bet you can have any job you want in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;232. INSULTED BECAUSE OF REPUTATION
 If gamPromo=232
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("You're nothing but a pussy, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("The wardens respect you more than the inmates..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;233. PRAISED BECAUSE OF REPUTATION
 If gamPromo=233
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("I wish i had your reputation, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I bet it keeps a lot of people off your back..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;234. INSULTED BECAUSE OF FINANCES
 If gamPromo=234
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", is $")+GetFigure$(gamMoney(slot))+translate(" all you're worth?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("That's loose change to me, you peasant!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;235. PRAISED BECAUSE OF FINANCES
 If gamPromo=235
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("I wish i was as rich as you, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I bet $")+GetFigure$(gamMoney(slot))+translate(" makes life a lot easier in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;236. INSULTED BECAUSE FOR BEING FAT
 If gamPromo=236
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Eeewww! You make me sick, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Take your fat ass somewhere else..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;237. INSULTED BECAUSE FOR BEING SKINNY
 If gamPromo=237
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Haha! You're a little runt, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'd love to break that pencil neck of yours..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;238. INSULTED FOR INFERIOR CRIME
 If gamPromo=238
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", you think you're a big man"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("because you're doing time for ")+Lower$(textCrime$(charCrime(pChar(v))))+translate("?!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Well, screw that because i'm in here for"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(Lower$(textCrime$(charCrime(pChar(cyc))))+translate(" - and that's even worse!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;239. DISGUSTED BY SUPERIOR CRIME
 If gamPromo=239
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear you're"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("in here for ")+Lower$(textCrime$(charCrime(pChar(v))))+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("Animals like you should be in another prison!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I'm only in here for ")+Lower$(textCrime$(charCrime(pChar(cyc))))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;240. COMFORTED ABOUT CRIME
 If gamPromo=240
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear you're"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("in here for ")+Lower$(textCrime$(charCrime(pChar(v))))+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Don't worry, i'm sure you didn't do it!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I've been accused of ")+Lower$(textCrime$(charCrime(pChar(cyc))))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;241. RIDICULED FOR WORKING
 If gamPromo=241
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Haha! Look at ")+charName$(pChar(v))+translate(" working!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Who are you trying to impress?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Only the wardens respect a hard worker!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Real thugs earn their money doing dirt..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;242. BLOCK FRICTION
 If gamPromo=242
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, what are you doing in the ")+textBlock$(GetBlock(gamLocation(slot)))+translate(" Block?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("This is where REAL criminals do their time!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("You better scurry back to the safety of the"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(textBlock$(charBlock(pChar(v)))+translate(" Block before you get in above your head!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;243. BLOCK COMRADERY
 If gamPromo=243
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, you're from the ")+textBlock$(charBlock(pChar(cyc)))+translate(" Block too?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Guys like us should stick together!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf  
 ;244. WARDEN APPEALS TO YOUR INTELLIGENCE
 If gamPromo=244
  ;intro
  optionA$=translate("Yes, help warden...") : optionB$=translate("No, forget it!") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i hear that you're an"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("intelligent guy? Maybe you can help me..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I haven't got a clue how to invest the money i"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("earn at this place! Can you make it work for me?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Thanks, that should boost my pathetic pension!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Maybe i can return the favour some time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("I don't need the advice of a criminal anyway!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You'd probably land me behind bars with you..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;245. WARDEN OFFERS TO REDUCE SENTENCE FOR MONEY
 If gamPromo=245
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(2)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Listen, ")+CellName$(pChar(v))+translate(", i've got the power to have"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("precious days taken off a guy's sentence..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("You normally have to earn that privilege, but"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("slip me $")+GetFigure$(promoCash)+translate(" and it might ease the process?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charSentence(pChar(v))=charSentence(pChar(v))-1 : statTim(6)=100
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1) 
    promoEffect=1
   EndIf
   Outline(translate("Thanks for your generous contribution! A nice guy"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("like you deserves to be out as soon as possible..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 
    charSentence(pChar(v))=charSentence(pChar(v))+1 : statTim(6)=-50 
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("You should be honoured that i made such an offer!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Since you don't appreciate it, i'll ADD time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;246. TIME OFF FOR GOOD BEHAVIOUR
 If gamPromo=246
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charSentence(pChar(v))=charSentence(pChar(v))-1 : statTim(6)=100
    charHappiness(pChar(v))=charHappiness(pChar(v))+10
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("That's good work, ")+CellName$(pChar(v))+translate("! You deserve to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("have time taken off for good behaviour..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;247. DEATH SENTENCE WHEN TOO LONG
 If gamPromo=247
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("How did you manage to clock up ")+GetFigure$(charSentence(pChar(v)))+translate(" days, ")+CellName$(pChar(v))+translate("?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("This prison is supposed to have a fast turnover..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))/2
    charReputation(pChar(v))=charReputation(pChar(v))+1
    For char=1 To no_chars
     If charRole(char)=1 Then ChangeRelationship(char,pChar(v),-1) : charAngerTim(char,pChar(v))=100000
    Next
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v
    promoEffect=1
   EndIf
   Outline(translate("Since you're not responding to rehabilitation,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("we might as well sentence you to DEATH!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf
 ;248. WORKING IS FUTILE
 If gamPromo=248
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("You're wasting your time, ")+CellName$(pChar(v))+translate("! There's"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("no money to be made during lockdown..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=-1
 EndIf 
 ;249. FRIEND ASKS FOR HELP
 If gamPromo=249
  ;intro
  optionA$=translate("Yes, help friend!") : optionB$=translate("No, sorry...") 
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", did you see what ")+charName$(charPromoRef(pChar(cyc))),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("just did? Help me get that son of a bitch!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    ChangeRelationship(charPromoRef(pChar(cyc)),pChar(v),-1)
    ChangeRelationship(pChar(cyc),charPromoRef(pChar(cyc)),-1) 
    charAngerTim(charPromoRef(pChar(cyc)),pChar(v))=1000
    charAngerTim(pChar(cyc),charPromoRef(pChar(cyc)))=1000
    promoEffect=1
   EndIf
   Outline(translate("Thank God! Now let's teach him a lesson!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("He's got no chance against both of us..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    ChangeRelationship(pChar(cyc),charPromoRef(pChar(cyc)),-1) 
    charAngerTim(charPromoRef(pChar(cyc)),pChar(cyc))=1000
    charAngerTim(pChar(cyc),charPromoRef(pChar(cyc)))=1000
    promoEffect=1
   EndIf
   Outline(translate("You're leaving me to face him on my own?! If"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("that's your idea of friendship then forget it!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;250. OFFER TO HEAL DISFIGUREMENT
 If gamPromo=250
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("How did you lose ")+DescribeLimb$(pChar(v))+translate(", ")+charName$(pChar(v))+translate("?!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You can't go through life with that wound..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("I can repair the damage if you want, but"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("i'll need $")+GetFigure$(promoCash)+translate(" to perform the operation?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    pInjured(v)=0 : charInjured(pChar(v))=0
    For limb=1 To 40
     If charScar(pChar(v),limb)=>5
      charScar(pChar(v),limb)=0
      pOldScar(v,limb)=-1
      pScar(v,limb)=charScar(pChar(v),limb)
      If pLimb(v,limb)>0
       ShowEntity pLimb(v,limb)
      EndIf
     EndIf
    Next
    charHappiness(pChar(v))=charHappiness(pChar(v))+10
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("There you go! You're as good as new! Just"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("look after yourself a little better this time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 Then DamageRelationship(pChar(cyc),pChar(v),-1) : promoEffect=1
   Outline(translate("Fine! I'll leave you to struggle through"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("life as a disgusting little cripple..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;251. TANOY ANNOUNCES FALL OF WARDEN
 If gamPromo=251
  If promoTim>25 And promoTim<9975 Then ShowPhoto(promoVariable)
  If promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("ATTENTION! ")+charName$(promoVariable)+translate(" has been"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("incarcerated for being corrupt!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   If promoEffect=0
    charHappiness(promoVariable)=charHappiness(promoVariable)/2
    charRole(promoVariable)=0
    charCrime(promoVariable)=Rnd(1,15)
    charSentence(promoVariable)=Rnd(5,30) 
    AssignCell(promoVariable)
    If charCell(promoVariable)=charCell(gamChar(slot)) And charBlock(promoVariable)=charBlock(gamChar(slot))
     randy=Rnd(0,2)
     If randy=1 Or (randy=0 And charReputation(promoVariable)<charReputation(gamChar(slot))) Then charPromo(promoVariable,gamChar(slot))=Rnd(202,203)
     If randy=2 Or (randy=0 And charReputation(promoVariable)=>charReputation(gamChar(slot))) Then charPromo(promoVariable,gamChar(slot))=Rnd(203,204)
    EndIf
    For v=1 To no_plays
     If pChar(v)=promoVariable Then ApplyCostume(v)
    Next
    charName$(promoVariable)=Right$(charName$(promoVariable),Len(charName$(promoVariable))-6)
    charName$(promoVariable)=textFirstName$(Rnd(0,65))+charName$(promoVariable)
    promoEffect=1
   EndIf
   Outline(translate("He will now be known as ")+charName$(promoVariable),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("from Cell ")+charCell(promoVariable)+translate(" of the ")+textBlock$(charBlock(promoVariable))+translate(" Block..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : charSnapped(promoVariable)=0
 EndIf 
 ;252. FEEL ILL
 If gamPromo=252
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    pHealth(cyc)=pHealth(cyc)/2
    charHappiness(pChar(cyc))=charHappiness(pChar(cyc))/2
    promoEffect=1
   EndIf
   Outline(translate("Oooh, i feel terrible all of a sudden!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I must be coming down with something..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 
  pInjured(cyc)=Rnd(1000,50000) 
 EndIf
 ;253. BUY QUALIFICATIONS
 If gamPromo=253
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"!" : optionB$=translate("No thanks...") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(1)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,2)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", don't waste your time"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("studying if you want to get a job!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promoTim>350 And promoTim<650
   Speak(cyc,3)
   Outline(translate("Just give me $")+GetFigure$(promoCash)+translate(" and i can forge the"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("qualifications for you if you want?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>650 Then camFoc=v
  If promoStage=0 And promoTim>675 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charIntelligence(pChar(v))=charIntelligence(pChar(v))+10
    If charIntelligence(pChar(v))<60 Then charIntelligence(pChar(v))=60
    If charIntelligence(pChar(v))>80 Then charIntelligence(pChar(v))=80
    charReputation(pChar(v))=charReputation(pChar(v))-1
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("No problem! Once i edit your test scores, people"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("will think you have an intelligence of ")+charIntelligence(pChar(v))+translate("%..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0 
    charReputation(pChar(v))=charReputation(pChar(v))+1
    DamageRelationship(pChar(cyc),pChar(v),-1)
    promoEffect=1
   EndIf
   Outline(translate("You'll never get anywhere with that attitude!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're not in high school anymore, bookworm..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;254. GET OUT OF MY WAY!
 If gamPromo=254
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", get out of my way!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You need to watch where you're going..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;255. ARE YOU FOLLOWING ME?
 If gamPromo=255
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", are you following me?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Get away from me before i kick you away!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;256. WHAT ARE YOU LOOKING AT?!
 If gamPromo=256
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("What are you looking at, ")+charName$(pChar(v))+translate("?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You don't eyeball me unless you want a fight!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;257. TOLD TO WASH SCARS
 If gamPromo=257
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-1
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", you look a mess!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Go and clean yourself up in the Bathroom..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;258. BEG FOR MERCY
 If gamPromo=258
  ;intro
  optionA$=translate("Yes, accept $")+GetFigure$(promoCash)+"..." : optionB$=translate("No, forget it!") 
  If promoStage=0 And promoTim<25 Then promoCash=Rnd(1,25) : promoCash=promoCash*10
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("STOP! I can't take any more, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Will you leave me alone if i give you $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)  
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,3)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Thank God! Here's your money."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Now please leave me in peace..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<10000 Then charAngerTim(pChar(cyc),pChar(v))=10000
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("Fine! If you want to fight to the death,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("i'll have to give you everything i've got!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;259. OFFERED MERCY
 If gamPromo=259
  ;intro
  optionA$=translate("Yes, pay $")+GetFigure$(promoCash)+"..." : optionB$=translate("No, do your worst!") 
  If promoStage=0 And promoTim<25 Then promoCash=GetPromoMoney(2)
  If promoStage=0 And promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("It's all over, ")+charName$(pChar(v))+translate("! You can't win,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("but i'll let you live if you give me $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=0 And promotim>325 Then camFoc=v
  If promoStage=0 And promoTim>350 Then promoStage=1 : foc=1 : keytim=20
  ;responses
  If promoStage=2 And promoTim>325 And promoTim<625
   Speak(cyc,2)
   If promoEffect=0 
    MakeDeal(cyc,v)
    PlaySound sCash : statTim(7)=-50
    gamMoney(slot)=gamMoney(slot)-promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    DamageRelationship(pChar(cyc),pChar(v),1)
    charAngerTim(pChar(cyc),pChar(v))=0
    promoEffect=1
   EndIf
   Outline(translate("I guess you can put a price on human life!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You just bought yourself a second chance..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=3 And promoTim>325 And promoTim<625
   Speak(cyc,1)
   If promoEffect=0
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<10000 Then charAngerTim(pChar(cyc),pChar(v))=10000
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("You don't think your life is worth $")+GetFigure$(promoCash)+translate("?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("I guess you won't mind losing it then!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoStage=>2 And promoTim>625 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;260. REPUTATION DISPUTED
 If gamPromo=260
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", i'm not scared of you!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Your status makes you a target to me..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<10000 Then charAngerTim(pChar(cyc),pChar(v))=10000
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("The only thing wrong with having a reputation"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("is that i can earn one by kicking your ass!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;261. HUMAN RIGHTS DONATION
 If gamPromo=261
  If promoTim<25 Then promoCash=Rnd(5,25) : promoCash=promoCash*10
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("Hello, i represent a human rights group and we'd"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("like to support you during this difficult time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,3)
   If promoEffect=0
    PlaySound sCash : statTim(7)=50
    gamMoney(slot)=gamMoney(slot)+promoCash
    charHappiness(pChar(v))=charHappiness(pChar(v))+10
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("Please accept a charitable donation of $")+GetFigure$(promoCash)+translate(" to"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("help fight your case and bring you some comfort!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;262. NOT FIT TO LEAVE
 If gamPromo=262
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("You've served your sentence, ")+CellName$(pChar(v))+translate(", but i'm"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("afraid you're still not fit to leave yet..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("To survive on the outside, you'll need strength,"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("agility, and intelligence ratings of at least 70%!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("Get to work on that, and perhaps you'll be"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("considered for release some other time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;263. NOT WEALTHY ENOUGH TO LEAVE
 If gamPromo=263
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   Outline(translate("You've served your sentence, ")+CellName$(pChar(v))+translate(", but i'm"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("afraid we can't let you leave just yet..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,2)
   Outline(translate("You'll need at least $1'000 to build a life on"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("the outside! You've only earned $")+GetFigure$(gamMoney(slot))+translate(" so far..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>675 And promoTim<975
   Speak(cyc,3)
   Outline(translate("Get to work on that, and perhaps you'll be"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("considered for release some other time..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>975 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;264. CRITICIZED OVER RECRUIT
 If gamPromo=264
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : ShowPhoto(charPromoRef(pChar(cyc)))
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", why did you recruit ")+charName$(charPromoRef(pChar(cyc)))+translate("?"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You know he's not suitable for ")+textGang$(charGang(pChar(cyc)))+translate("!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>350 And promoTim<650
   Speak(cyc,1)
   If promoEffect=0
    ChangeGang(pChar(cyc),0)
    ChangeGang(charPromoRef(pChar(cyc)),0)
    AngerGang(pChar(v),charGang(pChar(cyc)))
    AngerGang(charPromoRef(pChar(cyc)),charGang(pChar(cyc)))
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    promoEffect=1
   EndIf
   Outline(translate("Since you obviously can't be trusted, you're out"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("of the gang - and you can take that loser with you!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : RemovePromo(gamPromo)
 EndIf
 ;265. PRAISED OVER RECRUIT
 If gamPromo=265
  If promoTim>25 And promoTim<325
   Speak(cyc,3) : ShowPhoto(charPromoRef(pChar(cyc)))
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("Hey, ")+charName$(pChar(v))+translate(", good call on ")+charName$(charPromoRef(pChar(cyc)))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(textGang$(charGang(pChar(cyc)))+translate(" get stronger by the day..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1 : RemovePromo(gamPromo)
 EndIf
 ;266. RACIAL TENSION
 If gamPromo=266
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))-5
    charReputation(pChar(v))=charReputation(pChar(v))-1
    ChangeRelationship(pChar(cyc),pChar(v),-1)
    If charAngerTim(pChar(cyc),pChar(v))<100 Then charAngerTim(pChar(cyc),pChar(v))=100
    pAgenda(cyc)=2 : pFollowFoc(cyc)=v 
    promoEffect=1
   EndIf
   Outline(translate("You better watch your back, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("We don't like ")+textRace$(GetRace(pChar(v)))+translate(" people around here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;267. RACIAL COMRADERY
 If gamPromo=267
  If promoTim>25 And promoTim<325
   Speak(cyc,3)
   If promoEffect=0
    charHappiness(pChar(v))=charHappiness(pChar(v))+5
    charReputation(pChar(v))=charReputation(pChar(v))+1
    ChangeRelationship(pChar(cyc),pChar(v),1)
    promoEffect=1
   EndIf
   Outline(translate("We should stick together, ")+charName$(pChar(v))+translate("!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("They don't like ")+textRace$(GetRace(pChar(v)))+translate(" people in here..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;OPTION BOXES
 If promoStage=1
  If charBreakdown(gamChar(slot))>0 Then foc=2
  If foc=1 Then mood=3 Else mood=1
  If cyc>0
   If pChar(cyc)=gamChar(slot) Then Speak(cyc,mood)
  EndIf
  If v>0
   If pChar(v)=gamChar(slot) Then Speak(v,mood) 
  EndIf
  DrawOption(1,rX#(400),rY#(518),optionA$,translate(""))
  DrawOption(2,rX#(400),rY#(562),optionB$,translate(""))
 EndIf
 ;take photo
 If camFoc>0 And camFoc=oldFoc
  If charSnapped(pChar(camFoc))=0 And pSpeaking(camFoc)>0 And pGrappler(camFoc)=0 And AttackViable(camFoc)<>3
   If ReachedCord(camX#,camZ#,camTX#,camTZ#,5) And ReachedCord(camPivX#,camPivZ#,camPivTX#,camPivTZ#,5)
    TakePhoto(pChar(camFoc))
   EndIf
  EndIf
 EndIf
 ;put down phone
 If promoTim>9975
  If pPhone(v)>0 And pAnim(v)<>29 Then AnswerPhone(v,pPhone(v),29)
 EndIf
 ;end conversation
 If promoTim>10000
  For count=1 To no_plays
   If pChar(count)=gamChar(slot) Then camFoc=count : camType=1
  Next
  promoActor(1)=0 : promoActor(2)=0
  If (gamPromo>100 And gamPromo<120) Or (gamPromo=52 And promoStage=3) Then go=2
  If gamPromo=60 And promoStage=2 Then go=3
  gamPromo=0 : promoTim=50
 EndIf
End Function

;-----------------------------------------------------------------
;////////////////////// RELATED FUNCTIONS ////////////////////////
;-----------------------------------------------------------------

;TRIGGER PROMO
Function TriggerPromo(cyc,v,promo)
 camFoc=cyc : camType=10 : gamPromo=promo
 promoTim=0 : promoStage=0 : promoEffect=0
 promoActor(1)=cyc : promoActor(2)=v
 If cyc>0 And v>0 
  pFoc(cyc)=v : pFoc(v)=cyc
  pInteract(cyc,v)=1 : pInteract(v,cyc)=1 
  pPromoState(cyc)=Rnd(0,3) : pPromoState(v)=Rnd(0,3)
  If pAgenda(cyc)=2 Then pAgenda(cyc)=0
  If pAgenda(v)=2 Then pAgenda(v)=0 
 EndIf
End Function

;REMOVE SPECIFIC PROMO
Function RemovePromo(promo)
 For char=1 To no_chars
  If charPromo(char,gamChar(slot))=promo Then charPromo(char,gamChar(slot))=0
 Next
End Function

;REMOVE UNUSED PROMOS
Function RevisePromos()
 For cyc=1 To no_plays
  For v=1 To no_plays
   promo=charPromo(pChar(cyc),pChar(v))
   If promo=4 Or promo=5 Or promo=9 Or promo=10 Or promo=14 Or promo=15 Or promo=18 Then charPromo(pChar(cyc),pChar(v))=0
   If promo=40 Or promo=41 Or promo=43 Or promo=53 Or promo=72 Or promo=78 Or promo=79 Or promo=80 Then charPromo(pChar(cyc),pChar(v))=0
   If promo=249 Or promo=258 Or promo=259 Then charPromo(pChar(cyc),pChar(v))=0
  Next
 Next
End Function

;PREPARE TO SPEAK
Function Speak(cyc,expression)
 camFoc=cyc
 If cyc>0
  pSpeaking(cyc)=1
  If expression>0 Then pEyes(cyc)=expression
 EndIf
End Function

;RESET EXPRESSIONS
Function ResetExpressions(cyc) 
 pOldEyes(cyc)=pEyes(cyc)
 If screen=50 And cyc<>promoActor(1) And cyc<>promoActor(2)
  If pFoc(cyc)=0 Then pEyes(cyc)=2
  If pFoc(cyc)>0
   If charRelation(pChar(cyc),pChar(pFoc(cyc)))=-1 Then pEyes(cyc)=1
   If charRelation(pChar(cyc),pChar(pFoc(cyc)))=0 Then pEyes(cyc)=2
   If charRelation(pChar(cyc),pChar(pFoc(cyc)))=1 Then pEyes(cyc)=3
   If charAngerTim(pChar(cyc),pChar(pFoc(cyc)))>0 Then pEyes(cyc)=1
  EndIf
  If charGang(pChar(cyc))=6 Then pEyes(cyc)=3
  If charBreakdown(pChar(cyc))>0 Then pEyes(cyc)=1
 EndIf
 pSpeaking(cyc)=0
End Function

;APPLY FACIAL EXPRESSIONS
Function FacialExpressions(cyc)
 ;eye expressions
 If pEyes(cyc)<>pOldEyes(cyc) And pEyes(cyc)>0
  EntityTexture pLimb(cyc,1),tEyes(pEyes(cyc)),0,3
  pOldEyes(cyc)=pEyes(cyc)
 EndIf
 ;mouth expressions
 If pSpeaking(cyc)=1
  randy=Rnd(0,3)
  If randy=0 Then pMouth(cyc)=Rnd(-2,5)
  If pMouth(cyc)<0 Then pMouth(cyc)=0
  EntityTexture pLimb(cyc,1),tMouth(pMouth(cyc)),0,4
 EndIf
 If pSpeaking(cyc)=0 And pMouth(cyc)>0
  pMouth(cyc)=0
  EntityTexture pLimb(cyc,1),tMouth(0),0,4
 EndIf
End Function

;CONSTRUCT CELL ID
Function CellName$(char)
 If charBlock(char)=1 Then namer$="N-"+Dig$(charCell(char),100)
 If charBlock(char)=2 Then namer$="E-"+Dig$(charCell(char),100)
 If charBlock(char)=3 Then namer$="S-"+Dig$(charCell(char),100)
 If charBlock(char)=4 Then namer$="W-"+Dig$(charCell(char),100)
 Return namer$
End Function

;TAKE PHOTO
Function TakePhoto(char) 
 charPhoto(char)=CreateImage(rY#(300)+(rY#(300)/2),rY#(300))
 photoX#=415 : photoY#=280
 If screen=52 And char=101 Then photoX#=450 : photoY#=280
 If screen=52 And char=102 Then photoX#=365 : photoY#=280
 If GraphicsHeight()>600 Then photoY#=photoY#-10
 If GraphicsHeight()>768 Then photoY#=photoY#-20
 GrabImage charPhoto(char),rX#(photoX#),rY#(photoY#)
 ResizeImage charPhoto(char),150,100
 MaskImage charPhoto(char),255,0,255
 charSnapped(char)=1
End Function

;SHOW PHOTO IN PROMO
Function ShowPhoto(char)
 If charSnapped(char)>0
  DrawImage charPhoto(char),75,rY#(480)-50
  Color 0,0,0
  Rect 0,rY#(480)-100,150,100,0
 EndIf
End Function

;CHANGE RELATIONSHIP
Function ChangeRelationship(cyc,v,relation)
 charRelation(cyc,v)=relation
 charRelation(v,cyc)=relation
 If relation=1 Then charAngerTim(cyc,v)=0 : charAngerTim(v,cyc)=0
End Function

;DAMAGE RELATIONSHIP
Function DamageRelationship(cyc,v,effect)
 charRelation(cyc,v)=charRelation(cyc,v)+effect
 If charRelation(cyc,v)>1 Then charRelation(cyc,v)=1
 If charRelation(cyc,v)<-1 Then charRelation(cyc,v)=-1
 charRelation(v,cyc)=charRelation(cyc,v)
 If effect>0 Then charAngerTim(cyc,v)=0 : charAngerTim(v,cyc)=0
End Function

;CHANGE GANG
Function ChangeGang(char,gang)
 ;anger existing gang
 If charGang(char)>0 And charGang(char)<>gang And charGang(char)<>6
  AngerGang(char,charGang(char))
  For v=1 To no_chars
   If char=gamChar(slot) And charGang(v)=charGang(char) And charPromo(v,char)=0 Then charPromo(v,char)=42
  Next
 EndIf
 ;adapt to new one
 If gang>0 Then charGangHistory(char,gang)=1
 charGang(char)=gang
 GangAdjust(char)
 If screen=50 And go=0
  For v=1 To no_plays
   If pChar(v)=char Then ApplyCostume(v)
   ;risk being arrested
   If char=gamChar(slot) And charGang(char)>0 And charRole(pChar(v))=1 And Friendly(v,gamPlayer(slot))=0 And charBribeTim(pChar(v))=0 And AttackViable(v)=>1 And AttackViable(v)=<2 And pDazed(v)=0 And InProximity(v,gamPlayer(slot),50)
    If InLine(v,p(gamPlayer(slot)),60) 
     randy=Rnd(0,5)
     If randy=0 And gamWarrant(slot)<2 Then gamWarrant(slot)=2
    EndIf
   EndIf
  Next
 EndIf
End Function

;ANGER GANG
Function AngerGang(char,gang)
 For v=1 To no_chars  
  If charGang(v)=gang And gang<>6
   If charAngerTim(v,char)<10000 Then charAngerTim(v,char)=10000
   charRelation(v,char)=-1
  EndIf
 Next
End Function

;SUPERIORITY RANDOMIZER
Function SuperiorDice(cyc,v)
 repper=(charReputation(pChar(v))/10)-(charReputation(pChar(cyc))/10)
 If repper<1 Then repper=1
 chance=1000*repper
 If pAnim(cyc)=12 Or pAnim(cyc)=13 Or pAnim(v)=12 Or pAnim(v)=13 Then chance=chance/2 
 dice=Rnd(0,chance)
 Return dice
End Function

;INFERIORITY RANDOMIZER
Function InferiorDice(cyc,v)
 repper=5-((charReputation(pChar(v))/10)-(charReputation(pChar(cyc))/10))
 If repper<1 Then repper=1
 chance=1000*repper
 If pAnim(cyc)=12 Or pAnim(cyc)=13 Or pAnim(v)=12 Or pAnim(v)=13 Then chance=chance/2 
 dice=Rnd(0,chance)
 Return dice
End Function