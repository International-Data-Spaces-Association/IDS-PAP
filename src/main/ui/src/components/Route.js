import { Route, Switch } from "react-router-dom";
import React from "react";
import CountAccess from "../pages/CountAccess";
import Home from "../pages/Home";
import AnonymizeInRest from "../pages/AnonymizeInRest";
import ComplexPolicyForm from "../pages/ComplexPolicyForm";
import AnonymizeInTransit from "../pages/AnonymizeInTransit";
import LogAccess from "../pages/LogAccess";
import InformParty from "../pages/InformParty";
import DistributeData from "../pages/DistributeData";
import ProvideAccess from "../pages/ProvideAccess";
import DeleteDataAfter from "../pages/DeleteDataAfter";
import InterpretOdrlPolicy from "../pages/InterpretOdrlPolicy";
import ODRLCreator from "../pages/ODRLCreator";
import Account from "../pages/Account";
import SignIn from "../pages/SignIn";
import SignUp from "../pages/SignUp";
import ForgotPassword from "../pages/ForgotPassword";

export default function RouteToPage() {
  return (
    <Switch>
      <Route exact path="/">
        <Home />
      </Route>

      <Route exact path="/policy/InterpretOdrlPolicy">
        <InterpretOdrlPolicy />
      </Route>

      <Route exact path="/policy/ComplexPolicyForm">
        <ComplexPolicyForm />
      </Route>

      <Route exact path="/policy/ProvideAccessPolicyForm">
        <ProvideAccess />
      </Route>

      <Route exact path="/policy/CountAccessPolicyForm">
        <CountAccess />
      </Route>

      <Route exact path="/policy/DeletData">
        <DeleteDataAfter />
      </Route>

      <Route exact path="/policy/AnonymizeInRestPolicyForm">
        <AnonymizeInRest />
      </Route>

      <Route exact path="/policy/AnonymizeInTransitPolicyForm">
        <AnonymizeInTransit />
      </Route>

      <Route exact path="/policy/LogAccessPolicyForm">
        <LogAccess />
      </Route>

      <Route exact path="/policy/InformPolicyForm">
        <InformParty />
      </Route>

      <Route exact path="/policy/DistributeData">
        <DistributeData />
      </Route>
      <Route exact path="/ODRLCreator">
        <ODRLCreator/>
      </Route>
      <Route exact path="/account">
        <Account/>
      </Route>
      <Route exact path="/login">
        <SignIn/>
      </Route>
      <Route exact path="/register">
        <SignUp/>
      </Route>
      <Route exact path="/ForgotPassword">
        <ForgotPassword/>
      </Route>
    </Switch>
  );
}
