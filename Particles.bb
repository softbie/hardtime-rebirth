;//////////////////////////////////////////////////////////////////////////////
;------------------------- HARD TIME: PARTICLE EFFECTS ------------------------
;//////////////////////////////////////////////////////////////////////////////

;-----------------------------------------------------------------
;/////////////////////// PARTICLE EFFECTS ////////////////////////
;-----------------------------------------------------------------

;LOAD PARTICLE EFFECTS
Function LoadParticles()
 For cyc=1 To no_particles
  part(cyc)=LoadSprite("World/Sprites/Particle.bmp")
  EntityFX part(cyc),9
  partState(cyc)=0
  HideEntity part(cyc)
 Next
End Function

;CREATE PARTICLE EFFECT
Function CreateParticle(x#,y#,z#,style)
If optFX>0
 ;find empty spot
 cyc=0
 For count=1 To no_particles
  If partState(count)=0 Then cyc=count
 Next
 ;force spot!
 If cyc=0 Then cyc=Rnd(1,no_particles)
 ;activate new particle
 If cyc>0
  partX#(cyc)=x#
  partY#(cyc)=y#
  partZ#(cyc)=z#
  partA#(cyc)=Rnd(0,360)
  partGravity#(cyc)=Rnd(1.0,2.0)
  partFlight#(cyc)=0.3 
  partSize#(cyc)=Rnd(1.0,5.0)
  partAlpha#(cyc)=Rnd(0.5,0.9)
  partFade#(cyc)=0.02 
  ;unique traits
  partType(cyc)=style
  If partType(cyc)=1 ;fire
   EntityColor part(cyc),220,Rnd(0,100),0 
  EndIf
  If partType(cyc)=2 ;smoke
   randy=Rnd(0,100)
   EntityColor part(cyc),randy,randy,randy 
   partSize#(cyc)=Rnd(1.0,3.0)
   partFlight#(cyc)=0.1
   partGravity#(cyc)=0.1
   partAlpha#(cyc)=Rnd(0.4,0.8)
   partFade#(cyc)=0.01
  EndIf
  If partType(cyc)=3 ;blood
   EntityColor part(cyc),Rnd(50,200),0,0
   partFlight#(cyc)=0.2 ;: partSize#(cyc)=Rnd(2.0,6.0)
   partGravity#(cyc)=Rnd(0.5,1.0)
   partAlpha#(cyc)=Rnd(0.7,0.9)
   partFade#(cyc)=0.035
  EndIf
  If partType(cyc)=4 ;impact
   EntityColor part(cyc),Rnd(90,110),Rnd(70,90),Rnd(40,60);250,Rnd(100,200),0
   partFlight#(cyc)=0.15 ;: partSize#(cyc)=Rnd(2.0,6.0)
   partGravity#(cyc)=Rnd(0.5,1.0)
   partAlpha#(cyc)=Rnd(0.6,0.8)
   partFade#(cyc)=0.035
  EndIf
  If partType(cyc)=5 ;dust
   EntityColor part(cyc),100,80,50 
   partAlpha#(cyc)=Rnd(0.2,0.5)
   partSize#(cyc)=Rnd(1.0,3.0)
   partGravity#(cyc)=0.5
  EndIf
  If partType(cyc)=6 ;water
   EntityColor part(cyc),40,60,80
   partFlight#(cyc)=0.3 : partSize#(cyc)=Rnd(2.0,6.0)
   partGravity#(cyc)=Rnd(0.0,1.0)
   partAlpha#(cyc)=Rnd(0.3,0.7)
   partFade#(cyc)=0.02
  EndIf
  If partType(cyc)=7 ;small fire
   EntityColor part(cyc),220,Rnd(0,100),0
   partSize#(cyc)=Rnd(0.1,1.0)
   partGravity#(cyc)=0
   partFade#(cyc)=0.1
  EndIf
  If partType(cyc)=8 ;multi-coloured
   EntityColor part(cyc),Rnd(100,250),Rnd(100,250),Rnd(100,250) 
  EndIf
  If partType(cyc)=9 ;green mist
   EntityColor part(cyc),0,Rnd(100,180),0
   partGravity#(cyc)=Rnd(0.75,1.25)
   partFade#(cyc)=0.03
  EndIf
  If partType(cyc)=10 ;explosion (fire)
   EntityColor part(cyc),220,Rnd(0,100),0
   partSize#(cyc)=Rnd(5.0,10.0)
   partAlpha#(cyc)=Rnd(0.8,1.0)
  EndIf
  If partType(cyc)=11 ;explosion (foam)
   randy=Rnd(100,200)
   EntityColor part(cyc),randy,randy,randy
   partSize#(cyc)=Rnd(5.0,10.0)
   partAlpha#(cyc)=Rnd(0.6,0.8)
  EndIf
  If partType(cyc)=12 ;explosion (water)
   EntityColor part(cyc),40,80,120
   partSize#(cyc)=Rnd(5.0,10.0)
   partAlpha#(cyc)=Rnd(0.7,0.9)
  EndIf
  If partType(cyc)=13 ;explosion (beer)
   EntityColor part(cyc),Rnd(50,150),50,0
   partSize#(cyc)=Rnd(5.0,10.0)
   partAlpha#(cyc)=Rnd(0.7,0.9)
  EndIf
  If partType(cyc)=14 ;beer (small)
   EntityColor part(cyc),Rnd(50,150),50,0
   partSize#(cyc)=Rnd(0.5,2.0)
   partFlight#(cyc)=0
   partGravity#(cyc)=0
  EndIf
  ;reset & show
  partTim(cyc)=0
  partState(cyc)=1
  ShowEntity part(cyc)
  PositionEntity part(cyc),partX#(cyc),partY#(cyc),partZ#(cyc)
  RotateEntity part(cyc),0,partA#(cyc),0
  ScaleSprite part(cyc),partSize#(cyc),partSize#(cyc)
  EntityAlpha part(cyc),partAlpha#(cyc)
 EndIf
EndIf
End Function

;CREATE SPURT OF PARTICLES
Function CreateSpurt(x#,y#,z#,spread,density,style)
 If optFX>0
  ;reduce density
  If optFX=<1 Then density=density/2 
  ;deliver particles
  For count=1 To density 
   If style<99 Then CreateParticle(x#+Rnd(-spread,spread),y#+Rnd(-spread,spread),z#+Rnd(-spread,spread),style)
   If style=99 
    CreateParticle(x#+Rnd(-spread,spread),y#+Rnd(-spread,spread),z#+Rnd(-spread,spread),4)
    CreateParticle(x#+Rnd(-spread,spread),y#+Rnd(-spread,spread),z#+Rnd(-spread,spread),3) 
   EndIf
  Next
 EndIf
End Function

;PARTICLE CYCLE
Function ParticleCycle()
 For cyc=1 To no_particles
  If partState(cyc)>0
   If partType(cyc)<>7
    ;gravity
    If partGravity#(cyc)>-3.0 Then partGravity#(cyc)=partGravity#(cyc)-0.05
    If partType(cyc)=2 And partGravity#(cyc)<0.1 Then partGravity#(cyc)=0.1
    If partType(cyc)=14 And partGravity#(cyc)<-0.1 Then partGravity#(cyc)=-0.1
    partY#(cyc)=partY#(cyc)+partGravity#(cyc)
    ;flight
    MoveEntity part(cyc),0,0,partFlight#(cyc)
    partX#(cyc)=EntityX(part(cyc))
    partZ#(cyc)=EntityZ(part(cyc))
   EndIf
   ;update properties
   PositionEntity part(cyc),partX#(cyc),partY#(cyc),partZ#(cyc)
   RotateEntity part(cyc),0,partA#(cyc),0
   ScaleSprite part(cyc),partSize#(cyc),partSize#(cyc)
   ;transparency
   partAlpha#(cyc)=partAlpha#(cyc)-partFade#(cyc)
   EntityAlpha part(cyc),partAlpha#(cyc)
   ;clock
   partTim(cyc)=partTim(cyc)+1
   If partAlpha#(cyc)=<0 Or partTim(cyc)>1000 Then partState(cyc)=0
  EndIf
  ;remove
  If partState(cyc)=0 Then HideEntity part(cyc)
 Next
End Function

;-----------------------------------------------------------------
;///////////////////////// EXPLOSIONS ////////////////////////////
;-----------------------------------------------------------------

;TRIGGER EXPLOSION
Function CreateExplosion(source,entity,x#,y#,z#,style)
 If optFX>0
  ;find empty slot
  cyc=0
  For count=1 To no_explodes
   If exTim(count)=0 Then cyc=count
  Next
  If cyc=0 Then cyc=Rnd(1,no_explodes)
  ;initiate explosion
  If exType(cyc)=>11 
   ProduceSound(entity,sExplosion,0,0.5)
   ProduceSound(entity,sSplash,22050,0)
  Else
   ProduceSound(entity,sExplosion,0,1)
  EndIf
  exSource(cyc)=source
  exType(cyc)=style : exTim(cyc)=20
  exX#(cyc)=x# : exY#(cyc)=y# : exZ#(cyc)=z#
  For v=1 To no_plays
   exHurt(cyc,v)=0
  Next
  ;alert guards
  For count=1 To no_plays
   If charRole(pChar(count))=1
    pAgenda(count)=1
    pExploreX#(count)=x# : pExploreY#(count)=pY#(count) : pExploreZ#(count)=z#
    pSubX#(count)=9999 : pSubZ#(count)=9999
   EndIf
  Next
 EndIf
End Function

;EXPLOSION CYCLE
Function ExplosionCycle()
 For cyc=1 To no_explodes
  If exTim(cyc)>0
   ;blaze
   If exTim(cyc)=20 Or exTim(cyc)=15 Or exTim(cyc)=10 Or exTim(cyc)=5
    If optFX=<1 Then density=12 Else density=25
    For count=1 To density
     CreateParticle(exX#(cyc)+Rnd(-15,15),Rnd(exY#(cyc)-5,exY#(cyc)+10),exZ#(cyc)+Rnd(-15,15),exType(cyc))
    Next 
    If optFX=<1 Then density=7 Else density=15
    For count=1 To density
     CreateParticle(exX#(cyc)+Rnd(-10,10),exY#(cyc)+Rnd(-5,5),exZ#(cyc)+Rnd(-10,10),exType(cyc))
    Next
    If optFX=<1 Then density=2 Else density=5
    For count=1 To density
     CreateParticle(exX#(cyc)+Rnd(-5,5),exY#(cyc),exZ#(cyc)+Rnd(-5,5),exType(cyc))
    Next
    If optFX=<1 Then density=5 Else density=10
    For count=1 To density
     CreateParticle(exX#(cyc)+Rnd(-10,10),Rnd(exY#(cyc),exY#(cyc)+5),exZ#(cyc)+Rnd(-10,10),2)
    Next
   EndIf
   ;mess
   If exTim(cyc)=10 And exType(cyc)=>11
    CreatePool(exX#(cyc),12,exZ#(cyc),Rnd(10.0,15.0),1,exType(cyc)-9)
   EndIf
   ;human damage
   If exTim(cyc)=>5 And exTim(cyc)=<18
    For v=1 To no_plays
     If BlastProximity(cyc,pX#(v),pY#(v),pZ#(v),40) Then pDazed(v)=Rnd(100,300)
     If exHurt(cyc,v)=0 And BlastProximity(cyc,pX#(v),pY#(v),pZ#(v),30)
      charAttacker(pChar(v))=pChar(exSource(cyc))
      If exType(cyc)=10
       ProduceSound(p(v),sBlaze,22050,0.5)
       If pHealth(v)>0 Then ProduceSound(p(v),sPain(Rnd(1,8)),22050,0)
       CreateSpurt(pX#(v),EntityY(pLimb(v,1),1),pZ#(v),5,10,2)
       CreatePool(pX#(v),pGround#(v),pZ#(v),Rnd(5.0,10.0),3,1)
       ScarArea(v,0,0,0,1) : RiskInjury(v,25)
       pHealth(v)=pHealth(v)-10 
      EndIf
      pHealth(v)=pHealth(v)-10 : pHP(v)=0
      If AttackViable(v)<>3 Then pDT(v)=(150-pHealth(v))*2
      If AttackViable(v)=>1 And AttackViable(v)=<2 Then ChangeAnim(v,70)
      If AttackViable(v)=3 Then GroundReaction(v)
      If BlastProximity(cyc,pX#(v),pY#(v),pZ#(v),15)
       randy=Rnd(1,3)
       If randy=1 And pHealth(v)>0 Then ChangeAnim(v,80)
       If randy=2 And pHealth(v)>0 Then ChangeAnim(v,83)
       If randy=3 And pHealth(v)>0 Then ChangeAnim(v,86)
       If exType(cyc)=10 Then ScarArea(v,0,0,0,1) : RiskInjury(v,25) : pHealth(v)=pHealth(v)-10
       If AttackViable(v)<>3 Then pDT(v)=(200-pHealth(v))*2
      EndIf
      If exSource(cyc)>0
       RiskAnger(exSource(cyc),v)
       DamageRep(exSource(cyc),v,1)
       If exType(cyc)=10 Then DamageRep(exSource(cyc),v,1) 
       If exSource(cyc)=gamPlayer(slot) And gamMission(slot)<>11 And gamMission(slot)<>12 
        For count=1 To no_plays
         If charRole(pChar(count))=1 And Friendly(count,gamPlayer(slot))=0 And charBribeTim(pChar(count))=0 And AttackViable(count)=>1 And AttackViable(count)=<2 And pDazed(count)=0
          If InLine(count,p(gamPlayer(slot)),60) Or InLine(count,p(v),60)
           randy=Rnd(0,20)
           If exType(cyc)=10 Then randy=Rnd(0,5)
           If randy=0 And gamWarrant(slot)<4 Then gamWarrant(slot)=4 : gamItem(slot)=pWeapon(cyc) ;prosecuted for carrying
           If randy=1 And gamWarrant(slot)<10 Then gamWarrant(slot)=10 : gamItem(slot)=pWeapon(cyc) ;prosecuted for assault
          EndIf
         EndIf
        Next
       EndIf
      EndIf
      exHurt(cyc,v)=1
     EndIf
    Next
   EndIf
   ;expire
   exTim(cyc)=exTim(cyc)-1
  EndIf
 Next
End Function

;BLAST PROXIMITY
Function BlastProximity(cyc,x#,y#,z#,range)
 value=0
 If x#>exX#(cyc)-range And x#<exX#(cyc)+range And z#>exZ#(cyc)-range And z#<exZ#(cyc)+range And y#>exY#(cyc)-50 And y#<exY#(cyc)+50
  value=1
 EndIf
 Return value
End Function

;-----------------------------------------------------------------
;//////////////////////////// POOLS //////////////////////////////
;-----------------------------------------------------------------

;LOAD POOLS
Function LoadPools()
 For cyc=1 To no_pools
  pool(cyc)=LoadSprite("World/Sprites/Pool.png",4)
  SpriteViewMode pool(cyc),2
  HideEntity pool(cyc)
  poolState(cyc)=0
 Next
End Function

;PRODUCE POOL
Function CreatePool(x#,y#,z#,size#,layers,style)
 If optGore=>2
  For count=1 To layers
   ;find empty spot
   cyc=0
   For count=1 To no_pools
    If poolState(count)=0 Then cyc=count
   Next
   ;force spot!
   If cyc=0 Then cyc=Rnd(1,no_pools)
   ;generate pool
   poolX#(cyc)=x# : poolZ#(cyc)=z#
   If count>1 Then poolX#(cyc)=x#+Rnd(-5,5) : poolZ#(cyc)=z#+Rnd(-5,5)
   poolA#(cyc)=Rnd(0,360) : poolY#(cyc)=y#
   poolSize#(cyc)=size# : poolAlpha#(cyc)=0.7 
   poolState(cyc)=1 : ShowEntity pool(cyc)
   ;colour variations
   poolType(cyc)=style
   If style=1 Then EntityColor pool(cyc),Rnd(150,220),0,0 ;blood
   If style=2 Then EntityColor pool(cyc),255,255,255 ;foam
   If style=3 Then EntityColor pool(cyc),100,200,255 ;water
   If style=4 Then EntityColor pool(cyc),150,50,0 ;beer
  Next
 EndIf
End Function

;POOL CYCLE
Function PoolCycle()
 For cyc=1 To no_pools
  If poolState(cyc)=1
   ;location
   PositionEntity pool(cyc),poolX#(cyc),poolY#(cyc),poolZ#(cyc)
   RotateEntity pool(cyc),90,poolA#(cyc),0
   ;fade away
   poolAlpha#(cyc)=poolAlpha#(cyc)-0.0005
   If poolY#(cyc)<0 Then poolAlpha#(cyc)=poolAlpha#(cyc)-0.001
   EntityAlpha pool(cyc),poolAlpha#(cyc)
   ;shrink away
   poolSize#(cyc)=poolSize#(cyc)-0.01
   If poolY#(cyc)<0 Then poolSize#(cyc)=poolSize#(cyc)-0.01
   ScaleSprite pool(cyc),poolSize#(cyc),poolSize#(cyc)
   ;remove
   If poolSize#(cyc)<0.5 Or poolAlpha#(cyc)<0.01 Then poolState(cyc)=0 : HideEntity pool(cyc)
  EndIf
 Next
End Function