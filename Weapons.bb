;//////////////////////////////////////////////////////////////////////////////
;---------------------------- HARD TIME: WEAPONS ------------------------------
;//////////////////////////////////////////////////////////////////////////////

;-----------------------------------------------------------------
;////////////////////// LOAD WEAPONS /////////////////////////////
;----------------------------------------------------------------- 
Function LoadWeapons()
 For cyc=1 To no_weaps
  If weapLocation(cyc)=gamLocation(slot)
   ;generate
   ;Loader("Please Wait","Loading Weapon "+cyc+" of "+no_weaps)
   weap(cyc)=LoadAnimMesh("Weapons/"+weapFile$(weapType(cyc))+".3ds")
   ScaleEntity weap(cyc),0.4,0.4,0.4
   If weapTex(weapType(cyc))>0
    For count=1 To CountChildren(weap(cyc))
     EntityTexture GetChild(weap(cyc),count),weapTex(weapType(cyc))
    Next
   EndIf
   If weapShiny#(weapType(cyc))>0
    For count=1 To CountChildren(weap(cyc))
     EntityShininess GetChild(weap(cyc),count),weapShiny#(weapType(cyc))
    Next
   EndIf
   If weapName$(weapType(cyc))="Bottle" Then EntityAlpha weap(cyc),0.9
   ;collision detection
   EntityType weap(cyc),3,0
   EntityRadius weap(cyc),4,1
   weapGround(cyc)=CreatePivot()
   EntityType weapGround(cyc),4,0
   EntityRadius weapGround(cyc),4,1
   weapWall(cyc)=CreatePivot()
   EntityType weapWall(cyc),4,0
   EntityRadius weapWall(cyc),4,1 
   ;location
   weapY#(cyc)=weapY#(cyc)+10
   PositionEntity weap(cyc),weapX#(cyc),weapY#(cyc),weapZ#(cyc)
   RotateEntity weap(cyc),0,weapA#(cyc),0
   If weapState(cyc)=0 Then HideEntity weap(cyc)
   ;reset status
   weapGravity#(cyc)=-1.0 : weapFlight#(cyc)=0
   weapCarrier(cyc)=0 : weapOldScar(cyc)=-1
  EndIf
 Next
End Function

;-----------------------------------------------------------------
;////////////////////// WEAPON CYCLE /////////////////////////////
;-----------------------------------------------------------------
Function WeaponCycle()
 For cyc=1 To no_weaps
  If weapLocation(cyc)=gamLocation(slot) And weapState(cyc)>0
   ;store old positions 
   weapOldX#(cyc)=weapX#(cyc)
   weapOldY#(cyc)=weapY#(cyc)
   weapOldZ#(cyc)=weapZ#(cyc)
   ;honour collision detection 
   weapX#(cyc)=EntityX(weap(cyc))
   weapY#(cyc)=EntityY(weap(cyc))
   weapZ#(cyc)=EntityZ(weap(cyc))
   If weapCarrier(cyc)=0
    ;find baskets
    If gamLocation(slot)=2 And weapThrower(cyc)>0
     basket=FindChild(world,"Rim")
     If weapX#(cyc)>EntityX(basket,1)-6 And weapX#(cyc)<EntityX(basket,1)+6 And weapZ#(cyc)>EntityZ(basket,1)-6 And weapZ#(cyc)<EntityZ(basket,1)+6
      If weapY#(cyc)>EntityY(basket,1)-8 And weapY#(cyc)<EntityY(basket,1)-2 And weapOldY#(cyc)>EntityY(basket,1)-2
       ProduceSound(weap(cyc),weapSound(weapType(cyc)),22050,0)
       ProduceSound(weap(cyc),sBasket,22050,0)
       CreateSpurt(EntityX(basket,1),EntityY(basket,1),EntityZ(basket,1),3,10,5)
       charAgility(pChar(weapThrower(cyc)))=charAgility(pChar(weapThrower(cyc)))+Rnd(0,1)
       charHappiness(pChar(weapThrower(cyc)))=charHappiness(pChar(weapThrower(cyc)))+Rnd(1,5)
       randy=Rnd(0,5)
       If randy=0 Then charReputation(pChar(weapThrower(cyc)))=charReputation(pChar(weapThrower(cyc)))+1
       PointEntity weap(cyc),basket
       weapFlightA#(cyc)=EntityYaw(weap(cyc),1)
       If weapFlight#(cyc)<1.0 Then weapFlight#(cyc)=1.0
       If weapGravity#(cyc)<0 Then weapGravity#(cyc)=0
       weapThrower(cyc)=0
      EndIf
     EndIf 
    EndIf
    ;bounce on ground
    If weapY#(cyc)<EntityY(weapGround(cyc))+0.5 And weapGravity#(cyc)<-(weapWeight#(weapType(cyc))*5)
     If weapY#(cyc)>0 Then CreateSpurt(weapX#(cyc),weapY#(cyc),weapZ#(cyc),2,5,5)
     weapFlight#(cyc)=weapFlight#(cyc)-(weapFlight#(cyc)/6)
     If gotim>0 Then ProduceSound(weap(cyc),weapSound(weapType(cyc)),22050,weapGravity#(cyc)/8)
     weapGravity#(cyc)=MakePositive#(weapGravity#(cyc))
     weapGravity#(cyc)=weapGravity#(cyc)-(weapGravity#(cyc)/3)
     If weapGravity#(cyc)<0.25 Or gotim<0 Then weapGravity#(cyc)=0
     If weapFlight#(cyc)<weapGravity#(cyc)/5 Then weapFlight#(cyc)=weapGravity#(cyc)/5
     weapA#(cyc)=weapA#(cyc)+Rnd(-20,20)
     If weapGravity#(cyc)<1.0
      For v=1 To no_plays
       weapSting(cyc,v)=0
      Next
     EndIf
     For v=1 To no_plays
      If charRole(pChar(v))=1 And WeaponProximity(v,cyc,weapSize#(weapType(cyc))*5)
       pAgenda(v)=1 : pExploreY#(v)=pY#(v)
       pExploreX#(v)=weapX#(cyc) : pExploreZ#(v)=weapZ#(cyc)
       pSubX#(v)=9999 : pSubZ#(count)=9999
      EndIf
     Next
     ExplodeWeapon(cyc,0)
    EndIf
    ;bounce off wall
    If weapFlight#(cyc)>0 And weapY#(cyc)>EntityY(weapGround(cyc))+0.5
     If weapX#(cyc)>EntityX(weapWall(cyc))-2.0 And weapX#(cyc)<EntityX(weapWall(cyc))+2.0 And weapY#(cyc)>EntityY(weapWall(cyc))-2.0 And weapY#(cyc)<EntityY(weapWall(cyc))+2.0 And weapZ#(cyc)>EntityZ(weapWall(cyc))-2.0 And weapZ#(cyc)<EntityZ(weapWall(cyc))+2.0
      If gotim>0 Then ProduceSound(weap(cyc),weapSound(weapType(cyc)),22050,Rnd(0.1,0.3))
      CreateSpurt(weapX#(cyc),weapY#(cyc),weapZ#(cyc),1,5,5)
      weapGravity#(cyc)=1.0 : weapFlight#(cyc)=weapFlight#(cyc)/4
      weapFlightA#(cyc)=weapFlightA#(cyc)+180
      If gamLocation(slot)=2
       basket=FindChild(world,"Rim")
       If weapX#(cyc)>EntityX(basket,1)-100 And weapX#(cyc)<EntityX(basket,1)+100 And weapY#(cyc)>EntityY(basket,1)-20 And weapY#(cyc)<EntityY(basket,1)+30 And weapZ#(cyc)>EntityZ(basket,1)-100 And weapZ#(cyc)<EntityZ(basket,1)+100
        weapFlightA#(cyc)=weapFlightA#(cyc)+Rnd(-90,90) 
       EndIf
      EndIf
      weapA#(cyc)=weapA#(cyc)+Rnd(-20,20)
      ExplodeWeapon(cyc,0)
     EndIf
    EndIf
    ;bounce off humans
    If weapFlight#(cyc)>0 And weapY#(cyc)>EntityY(weapGround(cyc))+0.5
     For v=1 To no_plays
      range=weapSize#(weapType(cyc))-(weapSize#(weapType(cyc))/3)
      If AttackViable(v)=3 Then range=range*2
      If WeaponProximity(v,cyc,range) And weapY#(cyc)=>pY#(v)+5 And weapY#(cyc)=<EntityY(FindChild(p(v),"Head"),1)+5 And AttackViable(v)>0 And weapSting(cyc,v)=1
       charAttacker(pChar(v))=pChar(weapThrower(cyc))
       blocked=0 
       If pAnim(v)=>74 And pAnim(v)=<75 And InLine(v,weap(cyc),90) Then blocked=1
       ProduceSound(weap(cyc),weapSound(weapType(cyc)),22050,0) 
       ExplodeWeapon(cyc,0)
       If blocked=0
        If pHealth(v)>0 Then ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
        CreateSpurt(pX#(v),weapY#(cyc)-4,pZ#(v),2,10,99)
        ScarArea(v,weapX#(cyc),weapY#(cyc),weapZ#(cyc),5)
        If CountScars(v)=>2 Then ScarWeapon(cyc,0)
        pHealth(v)=pHealth(v)-Rnd(1,weapDamage(weapType(cyc)))
        pHP(v)=pHP(v)-Rnd(1,weapDamage(weapType(cyc)))
        pHurtA#(v)=weapFlightA#(cyc) : pStagger#(v)=0.5
        If AttackViable(v)=>1 And AttackViable(v)=<2
         If weapY#(cyc)=>pY#(v)+20 Then ChangeAnim(v,70)
         If weapY#(cyc)<pY#(v)+20 Then ChangeAnim(v,71)
         randy=Rnd(0,3)
         If randy=0 And weapY#(cyc)=>pY#(v)+20 Then pDazed(v)=Rnd(50,200)
        EndIf
        If AttackViable(v)=3 Then GroundReaction(v) : pDT(v)=pDT(v)-10
       EndIf
       If blocked=1
        CreateSpurt(pX#(v),weapY#(cyc)-4,pZ#(v),2,5,4)
        If pWeapon(v)>0 Then ProduceSound(p(v),weapSound(weapType(pWeapon(v))),22050,0) : DropWeapon(v,10)
       EndIf
       weapFlightA#(cyc)=weapFlightA#(cyc)+180
       weapA#(cyc)=weapA#(cyc)+Rnd(-50,50)
       weapGravity#(cyc)=1.0 : weapFlight#(cyc)=weapFlight#(cyc)/4
       If weapFlight#(cyc)>1.0 Then weapFlight#(cyc)=1.0
       If weapStyle(weapType(cyc))=7 And blocked=0
        ProduceSound(weap(cyc),sStab,22050,1)
        weapGravity#(cyc)=0 : weapFlight#(cyc)=0
        If weapName$(weapType(cyc))="Syringe" And pInjured(v)<100 Then pInjured(v)=Rnd(100,500)
       EndIf
       RiskAnger(weapThrower(cyc),v)
       DamageRep(weapThrower(cyc),v,1)
       weapSting(cyc,v)=0
      EndIf
     Next
    EndIf
    ;flight
    weapFlight#(cyc)=weapFlight#(cyc)-0.02
    If weapFlight#(cyc)<0 Then weapFlight#(cyc)=0
    If weapFlight#(cyc)>0
     If weapStyle(weapType(cyc))<>7 Then weapA#(cyc)=weapA#(cyc)+weapFlight#(cyc)
     RotateEntity weap(cyc),0,weapFlightA#(cyc),0
     MoveEntity weap(cyc),0,0,weapFlight#(cyc)
     If weapFlight#(cyc)>0.5
      randy=Rnd(0,5)
      If randy=<0 And weapY#(cyc)<0 Then CreateParticle(weapX#(cyc),weapY#(cyc),weapZ#(cyc),6)
      randy=Rnd(0,60)
      If randy=<weapScar(cyc) And weapScar(cyc)>1 Then CreatePool(weapX#(cyc),weapY#(cyc),weapZ#(cyc),Rnd(0.5,2.0),1,1) ;: CreateParticle(weapX#(cyc),weapY#(cyc),weapZ#(cyc),3)
     EndIf
    EndIf
    RotateEntity weap(cyc),0,weapA#(cyc),0
    ;gravity
    If weapY#(cyc)=weapOldY#(cyc) And weapGravity#(cyc)<0 Then weapGravity#(cyc)=0 
    If weapGravity#(cyc)>-8 Then weapGravity#(cyc)=weapGravity#(cyc)-weapWeight#(weapType(cyc))
    MoveEntity weap(cyc),0,weapGravity#(cyc),0
    If gotim<0 Then MoveEntity weap(cyc),0,-1.0,0
    ;identify ground 
    ResetEntity weapGround(cyc)
    PositionEntity weapGround(cyc),weapX#(cyc),weapY#(cyc)+5,weapZ#(cyc)
    RotateEntity weapGround(cyc),0,weapA#(cyc),0
    EntityType weapGround(cyc),4,0
    EntityRadius weapGround(cyc),4,1
    MoveEntity weapGround(cyc),0,-500,0 
    ;identify walls
    ResetEntity weapWall(cyc)
    PositionEntity weapWall(cyc),weapX#(cyc),weapY#(cyc),weapZ#(cyc)
    RotateEntity weapWall(cyc),0,weapFlightA#(cyc),0
    EntityType weapWall(cyc),4,0
    EntityRadius weapWall(cyc),4,1
    MoveEntity weapWall(cyc),0,0,500 
   EndIf
   ;follow carrier
   v=weapCarrier(cyc)
   If v>0
    ResetEntity weap(cyc)
    If weapStyle(weapType(cyc))=2 Then limb=pLimb(v,6) Else limb=pLimb(v,19)
    If EntityY(limb,1)>pY#(v)+10 Then PositionEntity weap(cyc),EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
    If EntityY(limb,1)=<pY#(v)+10 Then PositionEntity weap(cyc),EntityX(limb,1),pY#(v)+10,EntityZ(limb,1)
    weapA#(cyc)=EntityYaw(FindChild(p(v),weapFile$(weapType(cyc))),1)
    EntityType weap(cyc),3,0
    EntityRadius weap(cyc),4,1
   EndIf
   ;scarring
   ManageWeaponScars(cyc)
  EndIf
 Next
End Function

;-----------------------------------------------------------------
;///////////////////////// BULLETS ///////////////////////////////
;-----------------------------------------------------------------

;LOAD BULLETS
Function LoadBullets()
 For cyc=1 To no_bullets
  bullet(cyc)=CreatePivot()
  EntityType bullet(cyc),4,0
  EntityRadius bullet(cyc),1,1
  HideEntity bullet(cyc)
  bulletState(cyc)=0
 Next
End Function

;FIRE BULLET
Function FireBullet(cyc)
 ;decrement ammo
 weapAmmo(pWeapon(cyc))=weapAmmo(pWeapon(cyc))-1
 If weapAmmo(pWeapon(cyc))<0 Then weapAmmo(pWeapon(cyc))=0
 weapClip(pWeapon(cyc))=weapClip(pWeapon(cyc))-1
 If weapClip(pWeapon(cyc))<0 Then weapClip(pWeapon(cyc))=0
 ;find slot
 v=0
 For count=1 To no_bullets
  If bulletState(count)=0 Then v=count
 Next
 If v=0 Then v=Rnd(1,no_bullets)
 ;initiate bullet
 If weapStyle(weapType(pWeapon(cyc)))=3 
  limb=FindChild(p(cyc),"FlameB")
  PositionEntity bullet(v),EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
  bulletXA#(v)=-EntityPitch(limb,1) : bulletYA#(v)=EntityYaw(limb,1)+90 : bulletZA#(v)=EntityRoll(limb,1)
 EndIf
 If weapStyle(weapType(pWeapon(cyc)))=4
  limb=FindChild(p(cyc),"FlameA")
  PositionEntity bullet(v),EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1)
  bulletXA#(v)=EntityPitch(limb,1) : bulletYA#(v)=EntityYaw(limb,1)+90 : bulletZA#(v)=EntityRoll(limb,1) 
 EndIf
 bulletShooter(v)=cyc
 bulletState(v)=1 : bulletTim(v)=0
 ShowEntity bullet(v)
 ;alert guards
 charReputation(pChar(cyc))=charReputation(pChar(cyc))+Rnd(0,1) 
 For count=1 To no_plays
  If charRole(pChar(count))=1
   If pWeapFoc(count)<>pWeapon(cyc) Then pSubX#(count)=9999 : pSubZ#(count)=9999
   pAgenda(count)=4 : pWeapFoc(count)=pWeapon(cyc)
  EndIf
 Next
End Function

;BULLET CYCLE
Function BulletCycle()
For cyc=1 To no_bullets
 If bulletState(cyc)=1
  For depth=1 To 10
   ;honour collision detection 
   bulletX#(cyc)=EntityX(bullet(cyc))
   bulletY#(cyc)=EntityY(bullet(cyc))
   bulletZ#(cyc)=EntityZ(bullet(cyc))
   ;flight
   RotateEntity bullet(cyc),bulletXA#(cyc),bulletYA#(cyc),bulletZA#(cyc)
   MoveEntity bullet(cyc),0,0,10
   ;scenery contact
   If bulletState(cyc)=1
    For count=1 To CountCollisions(bullet(cyc))
     If CollisionEntity(bullet(cyc),count)>0
      ProduceSound(bullet(cyc),sRicochet(Rnd(1,3)),22050,0)
      ProduceSound(bullet(cyc),sImpact(Rnd(4,5)),22050,0)
      CreateSpurt(bulletX#(cyc),bulletY#(cyc)-5,bulletZ#(cyc),0,5,4)
      bulletState(cyc)=0 : HideEntity bullet(cyc) 
     EndIf
    Next
   EndIf
   ;human contact
   For v=1 To no_plays
    range=10
    If AttackViable(v)=3 Then range=20
    If bulletX#(cyc)>pX#(v)-range And bulletX#(cyc)<pX#(v)+range And bulletZ#(cyc)>pZ#(v)-range And bulletZ#(cyc)<pZ#(v)+range And bulletY#(cyc)=>pY#(v) And bulletY#(cyc)=<EntityY(FindChild(p(v),"Head"),1)+5
     If AttackViable(v)>0 And bulletState(cyc)=1
      charAttacker(pChar(v))=pChar(bulletShooter(cyc))
      ProduceSound(p(v),sImpact(Rnd(4,5)),22050,0)
      ProduceSound(p(v),sStab,22050,0)
      If pHealth(v)>0 Then ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
      CreateSpurt(pX#(v),bulletY#(cyc)-5,pZ#(v),0,5,99)
      ScarArea(v,bulletX#(cyc),bulletY#(cyc)-5,bulletZ#(cyc),2)
      CreatePool(bulletX#(cyc),pGround#(v),bulletZ#(cyc),Rnd(2.0,8.0),1,1)
      If AttackViable(v)=>1 And AttackViable(v)=<2
       If bulletY#(cyc)=>pY#(v)+20 And pAnim(v)<>70 Then ChangeAnim(v,70)
       If bulletY#(cyc)<pY#(v)+20 And pAnim(v)<>71 Then ChangeAnim(v,71)
       pDT(v)=(150-pHealth(v))*2
      EndIf 
      If AttackViable(v)=3 And pAnim(v)<>72 And pAnim(v)<>73 Then GroundReaction(v) : pDT(v)=pDT(v)-10
      pHealth(v)=pHealth(v)-Rnd(1,5) : pHP(v)=pHP(v)-Rnd(1,5) 
      pHurtA#(v)=bulletYA#(cyc) : pStagger#(v)=0.6 
      RiskAnger(bulletShooter(cyc),v)
      DamageRep(bulletShooter(cyc),v,0)
      bulletState(cyc)=0 : HideEntity bullet(cyc) 
     EndIf
    EndIf
   Next 
  Next
  ;range limit
  bulletTim(cyc)=bulletTim(cyc)+1
  If bulletTim(cyc)>5 Then bulletState(cyc)=0 : HideEntity bullet(cyc)
 EndIf
Next
End Function

;--------------------------------------------------------------
;////////////////// RELATED FUNCTIONS /////////////////////////
;--------------------------------------------------------------

;PREPARE CREATED WEAPONS
Function PrepareCreations()
 For cyc=1 To 6
  If kitState(cyc)>0
   If weapCreate(kitType(cyc))>0
    ;find slot
    newbie=0
    For v=1 To no_weaps
     If weapLocation(v)=0 Or (weapState(v)=0 And weapLocation(v)<>gamLocation(slot)) Then newbie=v : Exit
    Next
    ;force slot
    If newbie=0
     Repeat
      newbie=Rnd(0,no_weaps) 
     Until FindCarrier(newbie)=0 And weapLocation(newbie)<>gamLocation(slot)
    EndIf
    ;fill slot
    If newbie>0
     weapLocation(newbie)=gamLocation(slot)
     weapType(newbie)=kitType(cyc) : weapState(newbie)=0
    EndIf
   EndIf
  EndIf
 Next
End Function

;CREATE WEAPON
Function CreateWeapon(weapon,x#,y#,z#)
 ;find slot
 cyc=0
 For v=1 To no_weaps
  If weapType(v)=weapon And weapLocation(v)=gamLocation(slot) And weapState(v)=0 Then cyc=v
 Next
 ;force slot
 If cyc=0
  For v=1 To no_weaps
   If weapType(v)=weapon And weapLocation(v)=gamLocation(slot) Then cyc=v
  Next
 EndIf
 ;fill slot
 If cyc>0
  ProduceSound(cam,sSwing,22050,0.5)
  CreateSpurt(x#,y#,z#,3,10,4)
  ResetEntity weap(cyc)
  weapX#(cyc)=x# : weapY#(cyc)=y# : weapZ#(cyc)=z#
  PositionEntity weap(cyc),weapX#(cyc),weapY#(cyc),weapZ#(cyc)
  EntityType weap(cyc),3,0
  EntityRadius weap(cyc),4,1
  weapA#(cyc)=Rnd(0,360) : weapFlightA#(cyc)=weapA#(cyc)
  weapGravity#(cyc)=weapWeight#(weapType(cyc))*5 : weapFlight#(cyc)=0
  weapScar(cyc)=0 : weapOldScar(cyc)=-1
  weapAmmo(cyc)=100 : weapClip(cyc)=10
  If weapStyle(weapType(cyc))=6 Then weapClip(cyc)=0
  If weapCarrier(cyc)>0 Then pWeapon(weapCarrier(cyc))=0 : weapCarrier(cyc)=0
  ShowEntity weap(cyc) : weapState(cyc)=1
  For v=1 To no_plays
   weapSting(cyc,v)=0
  Next
 EndIf
End Function

;FIND CARRIER
Function FindCarrier(cyc)
 value=0
 If cyc>0
  For v=1 To no_chars
   If charWeapon(v)=cyc Then value=v
  Next
 EndIf
 Return value
End Function

;ATTAIN WEAPON
Function AttainWeapon(cyc,v)
 ;bind to player
 pWeapon(cyc)=v
 weapCarrier(v)=cyc
 ;texturing issues
 If weapTex(weapType(v))>0
  EntityTexture FindChild(p(cyc),weapFile$(weapType(v))),weapTex(weapType(v))
 EndIf
 EntityShininess FindChild(p(cyc),weapFile$(weapType(v))),weapShiny#(weapType(v)) 
 ;switch display
 ShowEntity FindChild(p(cyc),weapFile$(weapType(v))) 
 HideEntity weap(v) : weapOldScar(v)=-1 
End Function

;DROP WEAPON
Function DropWeapon(cyc,chance)
 v=pWeapon(cyc)
 randy=Rnd(0,chance)
 If randy=0 And v>0
  ProduceSound(p(cyc),sShuffle(Rnd(1,3)),44100,0.5)
  If weapY#(v)<pY#(cyc)+10 Then weapY#(v)=pY#(cyc)+10
  If pAnim(cyc)=83 And pAnimTim(cyc)>10 Then weapFlightA#(v)=pA#(cyc)+135 Else weapFlightA#(v)=pA#(cyc)+315
  If weapStyle(weapType(v))=2
   If pAnim(cyc)=83 And pAnimTim(cyc)>10 Then weapFlightA#(v)=pA#(cyc)+270 Else weapFlightA#(v)=pA#(cyc)+45
  EndIf
  weapGravity#(v)=1.0 : weapFlight#(v)=0.5
  weapCarrier(v)=0 : pWeapon(cyc)=0  
  HideEntity FindChild(p(cyc),weapFile$(weapType(v)))
  ShowEntity weap(v) : weapOldScar(v)=-1
  For count=1 To no_plays
   weapSting(v,count)=0
  Next
 EndIf
End Function

;THROW WEAPON
Function ThrowWeapon(cyc)
 v=pWeapon(cyc)
 If v>0
  ;reset state
  weapFlightA#(v)=pA#(cyc) : weapFlight#(v)=1.5+weapGravity#(v)
  If weapStyle(weapType(v))=7 And pAnim(cyc)<>27
   weapFlight#(v)=weapFlight#(v)+(weapFlight#(v)/4) 
   weapGravity#(v)=weapGravity#(v)-(weapGravity#(v)/3)
   weapA#(v)=pA#(cyc)+180
  EndIf
  If pAnim(cyc)=27 
   weapFlight#(v)=weapFlight#(v)/2
   If weapGravity#(v)>weapWeight#(weapType(v))*20 Then weapGravity#(v)=weapWeight#(weapType(v))*20
  EndIf
  weapThrower(v)=cyc : weapCarrier(v)=0 
  pWeapon(cyc)=0 
  HideEntity FindChild(p(cyc),weapFile$(weapType(v)))
  ShowEntity weap(v) : weapOldScar(v)=-1
  ;make potent
  For count=1 To no_plays
   weapSting(v,count)=1
  Next
  weapSting(v,cyc)=0 
  If weapStyle(weapType(v))=6 Then weapClip(v)=1
 EndIf
End Function

;SCAR WEAPON
Function ScarWeapon(cyc,chance)
 randy=Rnd(0,chance)
 If randy=0 Then weapScar(cyc)=weapScar(cyc)+1
 If weapScar(cyc)>4 Then weapScar(cyc)=4 
End Function

;MANAGE WEAPON SCARS
Function ManageWeaponScars(cyc)
 If weapLocation(cyc)=gamLocation(slot)
  ;heal scars
  randy=Rnd(0,10000/gamSpeed(slot))
  If randy=0 Then weapScar(cyc)=weapScar(cyc)-1
  If randy=<10 And weapScar(cyc)>1 And weapY#(cyc)<0
   weapScar(cyc)=weapScar(cyc)-1
   CreateParticle(weapX#(cyc),weapY#(cyc),weapZ#(cyc),3)
  EndIf 
  If weapScar(cyc)<0 Then weapScar(cyc)=0
  ;prevent gore
  If optGore=0 And weapScar(cyc)>1 Then weapScar(cyc)=1
  ;apply scars
  If weapScar(cyc)<>weapOldScar(cyc)
   For limb=1 To CountChildren(weap(cyc))
    EntityTexture GetChild(weap(cyc),limb),tBodyScar(weapScar(cyc)),0,2
   Next
   If weapCarrier(cyc)>0
    limb=FindChild(p(weapCarrier(cyc)),weapFile$(weapType(cyc)))
    EntityTexture limb,tBodyScar(weapScar(cyc)),0,2 
   EndIf
   weapOldScar(cyc)=weapScar(cyc)
  EndIf
 EndIf
End Function

;FIND NEAREST WEAPON
Function NearestWeapon(cyc) 
 value=0 : hi#=9999
 If no_weaps>0
  For v=1 To no_weaps 
   If weapLocation(v)=gamLocation(slot) 
    distance#=GetDistance#(pX#(cyc),pZ#(cyc),weapX#(v),weapZ#(v))  
    If weapCarrier(v)>0 Then distance#=distance#*2
    If weapY#(v)<pY#(cyc)-30 Or weapY#(v)>pY#(cyc)+30 Then distance#=distance#*2
    If distance#<hi# And pWeaponTim(cyc,v)=0 And weapState(v)=1 Then value=v : hi#=distance#
   EndIf
  Next
 EndIf
 Return value
End Function

;WEAPON IN PROXIMITY?
Function WeaponProximity(cyc,v,range)
 value=0
 If weapLocation(v)=gamLocation(slot) And weapState(v)=1 And pY#(cyc)>weapY#(v)-30 And pY#(cyc)<weapY#(v)+30
  checkX#=pX#(cyc) : checkZ#=pZ#(cyc)
  If pGrappling(cyc)>0 Or pGrappler(cyc)>0 Then checkX#=EntityX(pLimb(cyc,30),1) : checkZ#=EntityZ(pLimb(cyc,30),1)
  If checkX#>weapX#(v)-range And checkX#<weapX#(v)+range And checkZ#>weapZ#(v)-range And checkZ#<weapZ#(v)+range Then value=1
 EndIf
 Return value
End Function

;FIND (SPONTANEOUS) WEAPON IMPACT
Function WeaponImpact(cyc,v,blocked)
 w=pWeapon(cyc)
 If w>0
  ;effect
  ProduceSound(p(v),weapSound(weapType(w)),22050,0)
  If weapStyle(weapType(w))=7 Then ProduceSound(p(v),sStab,22050,0)
  If weapDamage(weapType(w))>1 Then ScarArea(v,pX#(v),EntityY(pLimb(cyc,19),1),pZ#(v),2)
  charReputation(pChar(cyc))=charReputation(pChar(cyc))+Rnd(0,1+charRole(pChar(v)))
  charHappiness(pChar(v))=charHappiness(pChar(v))-1
  ;damage
  If blocked=1
   pHealth(v)=pHealth(v)-1
   pHP(v)=pHP(v)-1
  Else
   pHealth(v)=pHealth(v)-Rnd(1,weapDamage(weapType(w)))
   If pAnim(cyc)<>211 Then pHP(v)=pHP(v)-Rnd(1,weapDamage(weapType(w)))
   pDT(v)=pDT(v)+(weapDamage(weapType(w))*10)
   If weapName$(weapType(w))="Syringe" And pInjured(v)<100 Then pInjured(v)=Rnd(100,500)
  EndIf
 EndIf
End Function

;FIND SMASHES
Function FindSmashes(cyc)
 ;weapon contact
 For v=1 To no_weaps
  range=weapSize#(weapType(v))+(weapSize#(weapType(v))/2)
  If weapLocation(v)=gamLocation(slot) And weapState(v)=1 And WeaponProximity(cyc,v,range) And pY#(cyc)>weapY#(v)-5 And weapCarrier(v)=0
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   ProduceSound(weap(v),weapSound(weapType(v)),22050,0)
   If weapStyle(weapType(v))=7 Then ProduceSound(p(cyc),sStab,22050,1) 
   CreateSpurt(weapX#(v),weapY#(v),weapZ#(v),3,10,99)
   ScarArea(cyc,weapX#(v),pY#(cyc),weapZ#(v),5)
   If CountScars(cyc)=>2 Then ScarWeapon(v,1) : CreatePool(weapX#(v),pGround#(cyc),weapZ#(v),Rnd(3.0,10.0),2,1)
   If weapStyle(weapType(v))<>6 Then Animate weap(v),3,2.0,0,1
   weapA#(v)=weapA#(v)+Rnd(-30,30)
   weapX#(v)=weapX#(v)+Rnd(-2,2) : weapZ#(v)=weapZ#(v)+Rnd(-2,2)
   pHealth(cyc)=pHealth(cyc)-Rnd(1,weapDamage(weapType(v)))
   pDT(cyc)=pDT(cyc)+(10*weapDamage(weapType(v)))
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1
   charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
   RiskInjury(cyc,100)
   weapThrower(v)=0 : ExplodeWeapon(v,-1)
   If weapName$(weapType(v))="Syringe" And pInjured(cyc)<100 Then pInjured(cyc)=Rnd(100,500)
  EndIf
 Next
 ;human contact
 For v=1 To no_plays
  If InProximity(cyc,v,20) And AttackViable(v)=3
   ProduceSound(p(cyc),sPain(Rnd(1,8)),22050,0)
   ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
   ProduceSound(p(v),sImpact(6),22050,0)
   CreateSpurt(pX#(cyc),pY#(v),pZ#(cyc),3,10,99)
   ScarArea(cyc,pX#(cyc),pY#(v),pZ#(cyc),10)
   ScarArea(v,pX#(cyc),pY#(v),pZ#(cyc),10)
   If AttackViable(v)=3 Then GroundReaction(v)
   pHealth(cyc)=pHealth(cyc)-1 : pHP(cyc)=0 : RiskInjury(cyc,200)
   pHealth(v)=pHealth(v)-1 : pHP(v)=0 : RiskInjury(v,200)
   charHappiness(pChar(cyc))=charHappiness(pChar(cyc))-1
   charReputation(pChar(cyc))=charReputation(pChar(cyc))-1
   charHappiness(pChar(v))=charHappiness(pChar(v))-1
   charReputation(pChar(v))=charReputation(pChar(v))-1 
  EndIf
 Next
End Function

;EXPLODE WEAPON
Function ExplodeWeapon(cyc,chance)
 randy=Rnd(0,chance)
 If randy=0 And weapStyle(weapType(cyc))=6 And (weapClip(cyc)>0 Or chance<0)
  If weapType(cyc)=9 Then CreateExplosion(weapThrower(cyc),weap(cyc),weapX#(cyc),weapY#(cyc),weapZ#(cyc),10)
  If weapType(cyc)=19 Then CreateExplosion(weapThrower(cyc),weap(cyc),weapX#(cyc),weapY#(cyc),weapZ#(cyc),11)
  If weapType(cyc)=18 Then CreateExplosion(weapThrower(cyc),weap(cyc),weapX#(cyc),weapY#(cyc),weapZ#(cyc),13)
  weapState(cyc)=0
  HideEntity weap(cyc)
 EndIf
End Function

;EXHAUST DRUG
Function ExhaustDrug(cyc) 
 w=pWeapon(cyc)
 randy=Rnd(0,3)
 If randy=0 Then weapAmmo(w)=weapAmmo(w)-1
 If w>0 And weapAmmo(w)=<0
  ProduceSound(p(cyc),sSwing,22050,0.3)
  limb=FindChild(p(cyc),weapFile$(weapType(w)))
  CreateSpurt(EntityX(limb,1),EntityY(limb,1),EntityZ(limb,1),2,10,4)
  DropWeapon(cyc,0)
  weapState(w)=0
  HideEntity weap(w)
  ChangeAnim(cyc,21)
 EndIf
End Function