//
//  Button_FunViewController.h
//  Button Fun
//
//  Created by Anders on 11-2-26.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface Button_FunViewController : UIViewController {
	IBOutlet UILabel *statusText;
}

@property(retain, nonatomic) UILabel *statusText;

- (IBAction)buttonPressed:(id)sender;

@end

