package org.licket.framework.hippo;

import org.mozilla.javascript.ast.Name;

/**
 * @author activey
 */
public class NameBuilder extends AbstractAstNodeBuilder<Name> {

  private String identifier;

  private NameBuilder(String identifier) {
    this.identifier = identifier;
  }

  public static NameBuilder name(String identifier) {
    return new NameBuilder(identifier);
  }

  @Override
  public Name build() {
    Name name = new Name();
    name.setIdentifier(identifier);
    return name;
  }
}
